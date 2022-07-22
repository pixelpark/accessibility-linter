package com.github.bucherfa.accessibilitylinter.annotators

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.github.bucherfa.accessibilitylinter.misc.ConfigAxe
import com.github.bucherfa.accessibilitylinter.services.LinterService
import com.google.gson.JsonArray
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.components.service
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
        return CollectedInformation(file, getConfig(file))
    }

    override fun doAnnotate(collectedInformation: CollectedInformation?): List<CustomAnnotation>? {
        if (collectedInformation == null) {
            return listOf()
        }
        val file = collectedInformation.file
        val input = file.text
        val linterService = file.project.service<LinterService>()
        val response = linterService.runRequest(input, collectedInformation.config)
        var annotations: List<CustomAnnotation> = mutableListOf()
        if (response != null) {
            val element = response.get().element
            val result = element.getAsJsonArray("result")
            annotations = processResult(result)
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
                val message = "Accessibility Linter: ${annotation.message} (${annotation.type})"
                holder.createAnnotation(HighlightSeverity.WARNING, annotation.range, message, htmlTooltip)
            }
        }
        println("... finished")
    }

    private fun getConfig(file: PsiFile): ConfigAxe {
        val configFiles = FilenameIndex.getVirtualFilesByName("axe-linter.yml", GlobalSearchScope.projectScope(file.project))
        for (configFile in configFiles) {
            if (configFile.isDirectory) {
                continue
            }
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

    fun processResult(result: JsonArray): List<CustomAnnotation> {
        val annotations = mutableListOf<CustomAnnotation>()
        for (violation in result) {
            val violationObject = violation.asJsonObject
            val type = violationObject.get("type").asString
            val helpString = violationObject.get("help").asString
            val helpUrl = violationObject.get("helpUrl").asString
            for (occasion in violationObject.get("occasions").asJsonArray) {
                val occasionObject = occasion.asJsonObject
                val startOffset = occasionObject.get("startOffset").asInt
                val endOffset = occasionObject.get("endOffset").asInt
                val range = TextRange(startOffset, endOffset)
                annotations.add(CustomAnnotation(range, type, helpString, helpUrl))
            }
        }
        return annotations
    }
}
