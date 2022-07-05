package com.github.bucherfa.accessibilitylinter.services

import com.intellij.openapi.project.Project
import com.github.bucherfa.accessibilitylinter.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
