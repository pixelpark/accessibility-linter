package com.github.bucherfa.accessibilitylinter.annotators

import com.github.bucherfa.accessibilitylinter.services.LinterService
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile

class CustomAnnotation(val range: TextRange, val type: String, val message: String, val url: String)

class HtmlAnnotator : ExternalAnnotator<PsiFile, List<CustomAnnotation>>() {

    override fun collectInformation(file: PsiFile, editor: Editor, hasErrors: Boolean): PsiFile? {
        return file
    }

    override fun doAnnotate(collectedInformation: PsiFile?): List<CustomAnnotation>? {
        val input = collectedInformation!!.text
        val service =  ServiceManager.getService(collectedInformation.project, LinterService::class.java)
        val response = service.runRequest(input)
        val annotations: MutableList<CustomAnnotation> = mutableListOf()
        if (response != null) {
            val element = response.get().element
            val result = element.getAsJsonArray("result")
            for (violation in result) {
                val snippet = violation.asJsonObject.get("html").asString
                // TODO multiple occasions, ignore comments
                val startIndex = input.indexOf(snippet)
                if (startIndex < 0) {
                    println("Couldn't find startIndex for $snippet")
                    continue
                }
                val range = TextRange(startIndex, startIndex + snippet.length)
                val type = violation.asJsonObject.get("type").asString
                val helpString = violation.asJsonObject.get("help").asString
                val helpUrl = violation.asJsonObject.get("helpUrl").asString
                annotations.add(CustomAnnotation(range, type, helpString, helpUrl))
            }
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
    }
}
