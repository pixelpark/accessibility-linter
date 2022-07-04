package com.github.bucherfa.accessibilitylinter.annotators

import com.github.bucherfa.accessibilitylinter.AccessibilityLinterUtil
import com.github.bucherfa.accessibilitylinter.services.LinterService
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.util.ExecUtil
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.application.PluginPathManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import java.io.File
import java.nio.charset.Charset
import com.intellij.javascript.nodejs.npm.NpmManager
import com.intellij.openapi.components.ServiceManager

class CollectedInformation(val file: PsiFile)
class CustomAnnotation(val range: TextRange, val message: String) {
}

class HtmlAnnotator : ExternalAnnotator<PsiFile, List<CustomAnnotation>>() {

    override fun collectInformation(file: PsiFile, editor: Editor, hasErrors: Boolean): PsiFile? {
        println("Hello")
        return file
    }

    override fun doAnnotate(collectedInformation: PsiFile?): List<CustomAnnotation>? {
        println(123)
        val input = collectedInformation!!.text
        println(input)
        val service =  ServiceManager.getService(collectedInformation.project, LinterService::class.java)
        val response = service.runRequest(input)
        print(response!!.get())
        val annotations: MutableList<CustomAnnotation> = mutableListOf()
        response.get().let {
            val element = it.element
            val result = element.getAsJsonArray("result")
            for (violation in result) {
                val snippet = violation.asJsonObject.get("html").asString
                val startIndex = input.indexOf(snippet)
                if (startIndex < 0) {
                    println("Couldn't find startIndex for $snippet")
                    continue
                }
                annotations.add(CustomAnnotation(TextRange(startIndex, startIndex + snippet.length), violation.asJsonObject.get("help").asString))
            }
        }
        return annotations
    }

    override fun apply(file: PsiFile, annotationResult: List<CustomAnnotation>?, holder: AnnotationHolder) {
        if (!annotationResult.isNullOrEmpty()) {
            for (annotation in annotationResult) {
                val annotationBuilder = holder.newAnnotation(HighlightSeverity.WARNING, annotation.message).range(annotation.range)
                annotationBuilder.create()
            }
        }
    }

}
