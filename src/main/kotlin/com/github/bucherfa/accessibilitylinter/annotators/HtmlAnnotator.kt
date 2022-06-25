package com.github.bucherfa.accessibilitylinter.annotators

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile

class CustomAnnotation(val range: TextRange, val message: String) {
}

class HtmlAnnotator : ExternalAnnotator<PsiFile, List<CustomAnnotation>>() {

    override fun collectInformation(file: PsiFile): PsiFile {
        return file
    }

    override fun collectInformation(file: PsiFile, editor: Editor, hasErrors: Boolean): PsiFile {
        return file
    }

    override fun doAnnotate(collectedInfo: PsiFile?): List<CustomAnnotation>? {
        val annotations: MutableList<CustomAnnotation> = mutableListOf()
        val content = collectedInfo!!.text
        println(content)
        val startIndex = content.indexOf("Hello World")
        println(startIndex)
        if (startIndex > 0) {
            val newAnnotation = CustomAnnotation(TextRange(startIndex, startIndex + "Hello World".length), "Please include the whole universe! ;)")
            annotations.add(newAnnotation)
        }
        println("Length: " + annotations.size)
        return annotations
        //return super.doAnnotate(collectedInfo)
    }

    override fun apply(file: PsiFile, annotationResult: List<CustomAnnotation>?, holder: AnnotationHolder) {
        if (!annotationResult.isNullOrEmpty()) {
            for (annotation in annotationResult) {
                val annotationBuilder = holder.newAnnotation(HighlightSeverity.WARNING, annotation.message).range(annotation.range)
                annotationBuilder.create()
            }
        }
        //super.apply(file, annotationResult, holder)
    }

}
