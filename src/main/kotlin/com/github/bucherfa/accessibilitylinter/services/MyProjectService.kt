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
    }
}
