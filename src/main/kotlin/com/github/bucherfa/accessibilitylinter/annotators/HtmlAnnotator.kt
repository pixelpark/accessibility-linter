package com.github.bucherfa.accessibilitylinter.annotators

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.github.bucherfa.accessibilitylinter.misc.ConfigAxe
import com.github.bucherfa.accessibilitylinter.services.LinterService
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import java.nio.file.Path

class CustomAnnotation(val range: TextRange, val type: String, val message: String, val url: String)
class CollectedInformation(val file: PsiFile, val config: ConfigAxe)

class HtmlAnnotator : ExternalAnnotator<CollectedInformation, List<CustomAnnotation>>() {

    override fun collectInformation(file: PsiFile, editor: Editor, hasErrors: Boolean): CollectedInformation? {
        println("Starting...")
        return CollectedInformation(file, getConfig(file, editor))
    }

    override fun doAnnotate(collectedInformation: CollectedInformation?): List<CustomAnnotation>? {
        if (collectedInformation == null) {
            return listOf()
        }
        val file = collectedInformation.file
        val input = file.text
        val service =  ServiceManager.getService(file.project, LinterService::class.java)
        val response = service.runRequest(input, collectedInformation.config)
        val annotations: MutableList<CustomAnnotation> = mutableListOf()
        if (response != null) {
            val element = response.get().element
            val result = element.getAsJsonArray("result")
            val unrecognizableViolations = mutableListOf<String>()
            val duplicateViolations = mutableListOf<String>()
            for (violation in result) {
                val type = violation.asJsonObject.get("type").asString
                val snippet = violation.asJsonObject.get("node").asJsonObject.get("source").asString
                // TODO multiple occasions
                val sanitizedInput = removeCommentsFromString(input, "<!--", "-->")
                val startIndex = sanitizedInput.indexOf(snippet)
                if (startIndex < 0) {
                    unrecognizableViolations.add("$type (${violation.asJsonObject.get("node").asJsonObject.get("ancestry").asString})")
                    continue
                }
                val range = TextRange(startIndex, startIndex + snippet.length)
                val helpString = violation.asJsonObject.get("help").asString
                val helpUrl = violation.asJsonObject.get("helpUrl").asString
                // avoid duplicates
                if (annotations.find { customAnnotation -> customAnnotation.type == type &&
                                customAnnotation.range.startOffset == range.startOffset &&
                                customAnnotation.range.startOffset == range.startOffset } == null) {
                    annotations.add(CustomAnnotation(range, type, helpString, helpUrl))
                } else {
                    duplicateViolations.add("$type (${violation.asJsonObject.get("node").asJsonObject.get("ancestry").asString})")
                }
            }
            if (unrecognizableViolations.size > 0)
                println("Unable to match ${unrecognizableViolations.size} violations.\n" +
                        "    $unrecognizableViolations")
            if (duplicateViolations.size > 0)
                println("${duplicateViolations.size} duplicate violations found.\n" +
                        "    $duplicateViolations")
        }
        return annotations
    }

    override fun apply(file: PsiFile, annotationResult: List<CustomAnnotation>?, holder: AnnotationHolder) {
        if (!annotationResult.isNullOrEmpty()) {
            for (annotation in annotationResult) {
                val adjustedMessage = annotation.message
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                val htmlTooltip = "<html><body>" +
                        "Accessibility Linter: $adjustedMessage " +
                        "(<a href=\"${annotation.url}\">${annotation.type}</a>)" +
                        "</body></html>"
                holder.createAnnotation(HighlightSeverity.WARNING, annotation.range, annotation.message, htmlTooltip)
//                holder.newAnnotation(HighlightSeverity.WARNING, annotation.message)
//                    .range(annotation.range)
//                    .create()
            }
        }
        println("... finished")
    }

    private fun getConfig(file: PsiFile, editor: Editor): ConfigAxe {
        val configFiles = FilenameIndex.getVirtualFilesByName(file.project, "axe-linter.yml", GlobalSearchScope.projectScope(file.project))
        for (configFile in configFiles) {
            //TODO check if configFile is dir
            val configFilePath = configFile.path
            val projectPath = file.project.basePath
            val configFileName = configFile.name
            if (!projectPath.isNullOrEmpty() && Path.of(projectPath, configFileName).toString() == configFilePath) {
                val mapper = ObjectMapper(YAMLFactory())
                return try {
                    mapper.readValue(Path.of(configFile.path).toFile(), ConfigAxe::class.java)
                } catch (e: Exception) {
                    //TODO user notification https://plugins.jetbrains.com/docs/intellij/notifications.html
                    ConfigAxe()
                }
            }
        }
        return ConfigAxe()
    }

    private fun removeCommentsFromString(string: String, startIndicator: String, endIndicator: String): String {
        var result = string;
        val regex = Regex("$startIndicator.*?$endIndicator")
        val occasions = regex.findAll(string)
        for (occasion in occasions) {
            val length = occasion.range.last - occasion.range.first + 1
            var replacementString = ""
            for (i in 0 until length) {
                replacementString += " "
            }
            result = result.replaceRange(occasion.range, replacementString)
        }
        return result
    }
}
