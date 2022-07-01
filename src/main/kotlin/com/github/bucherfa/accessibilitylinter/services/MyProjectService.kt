package com.github.bucherfa.accessibilitylinter.services

import com.intellij.openapi.project.Project
import com.github.bucherfa.accessibilitylinter.MyBundle
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.javascript.nodejs.interpreter.NodeJsInterpreter
import com.intellij.javascript.nodejs.interpreter.NodeJsInterpreterManager
import com.intellij.javascript.nodejs.interpreter.local.NodeJsLocalInterpreter
import com.intellij.javascript.nodejs.npm.NpmManager
import com.intellij.lang.javascript.service.JSLanguageServiceUtil
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ServiceManager

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
//        val nodeJsInstance = NodeJsInterpreterManager.getInstance(project)
//        val npmInstance = NpmManager.getInstance(project)
//        val a = npmInstance.getPackage(nodeJsInstance.interpreter)
//        npmInstance.npmInstallPresentableText
//        println(npmInstance.npmInstallPresentableText)
//        println("xD")
//        println(nodeJsInstance.interpreter?.presentableName ?: "node1")
//        println(nodeJsInstance.interpreter?.referenceName ?: "node2")
//        val b = GeneralCommandLine(nodeJsInstance.interpreter?.presentableName ?: "node")
//        val service =  ServiceManager.getService(project, LinterService::class.java)
//        val response = service.runRequest("<html></html>")
//        println(response!!.get())
//        println("############")
        //JSLanguageServiceUtil.getPluginDirectory(this.javaClass, )

    }
}
