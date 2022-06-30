package com.github.bucherfa.accessibilitylinter.annotators

import com.github.bucherfa.accessibilitylinter.AccessibilityLinterUtil
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

class CollectedInformation(val temporaryFilePath: String, val file: PsiFile)
class CustomAnnotation(val range: TextRange, val message: String) {
}

class HtmlAnnotator : ExternalAnnotator<CollectedInformation, List<CustomAnnotation>>() {

    override fun collectInformation(file: PsiFile, editor: Editor, hasErrors: Boolean): CollectedInformation? {
        val content = file.text
        val pluginPath = PluginPathManager.getPluginHomePath(AccessibilityLinterUtil.PACKAGE_NAME)
        println("pluginPath ci: $pluginPath")
        val pluginDirectory = File(pluginPath)
        val temporaryFile = File.createTempFile("temp.", ".html")
        val temporaryFilePath = temporaryFile.absolutePath
        File(temporaryFilePath).appendText(content)
        val command = listOf("node", "/home/pixel/.nvm/versions/node/v16.14.0/lib/node_modules/accessibility-checker/bin/achecker.js","--reportLevels","violation","--outputFormat","json","--policies","IBM_Accessibility,WCAG_2_1", "--outputFolder","/tmp", temporaryFilePath)
        //val command = listOf("achecker", "--reportLevels violation --outputFormat json --policies IBM_Accessibility,WCAG_2_1,WCAG_2_0", temporaryFilePath)
        val generalCommandLine = GeneralCommandLine(command)
        generalCommandLine.charset = Charset.forName("UTF-8")
        //generalCommandLine.workDirectory = pluginDirectory
        val result = ExecUtil.execAndGetOutput(generalCommandLine)
        print(result.stdout)
        File(temporaryFilePath).delete()
        return CollectedInformation(temporaryFilePath, file)
    }

    override fun doAnnotate(collectedInfo: CollectedInformation?): List<CustomAnnotation>? {
//        val temporaryFilePath = collectedInfo?.temporaryFilePath
//        if (temporaryFilePath != null) {
//            val command = listOf("achecker --reportLevels violation --outputFormat json --policies IBM_Accessibility,WCAG_2_1,WCAG_2_0", temporaryFilePath)
//            val pluginPath = PluginPathManager.getPluginHomePath(AccessibilityLinterUtil.PACKAGE_NAME)
//            println("pluginPath da: $pluginPath")
//            val pluginDirectory = File(pluginPath)
//            val generalCommandLine = GeneralCommandLine(command)
//            generalCommandLine.charset = Charset.forName("UTF-8")
//            //generalCommandLine.workDirectory = pluginDirectory
//            val result = ExecUtil.execAndGetOutput(generalCommandLine)
//            print(result.stdout)
//            File(temporaryFilePath).delete()
//        }
        val annotations: MutableList<CustomAnnotation> = mutableListOf()
//        val startIndex = content.indexOf("Hello World")
//        println(startIndex)
//        if (startIndex > 0) {
//            val newAnnotation = CustomAnnotation(TextRange(startIndex, startIndex + "Hello World".length), "Please include the whole universe! ;)")
//            annotations.add(newAnnotation)
//        }
//        println("Length: " + annotations.size)
//        println("result: " + result.stdout)
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
