package com.github.bucherfa.accessibilitylinter.services

import com.intellij.lang.javascript.service.*
import com.intellij.lang.javascript.service.protocol.*
import com.intellij.openapi.project.Project
import com.intellij.util.Consumer
import com.intellij.util.EmptyConsumer
import java.util.concurrent.Future

class LinterService(project: Project) :
    JSLanguageServiceBase(project) {

    override fun createLanguageServiceQueue(): JSLanguageServiceQueue {
        val protocol = ServiceProtocol(myProject, EmptyConsumer.getInstance<Any>())

        return JSLanguageServiceQueueImpl(myProject, protocol, myProcessConnector, myDefaultReporter,
            JSLanguageServiceDefaultCacheData()
        )
    }

    override fun needInitToolWindow() = false

    fun runRequest(input: String): Future<JSLanguageServiceAnswer>? {
        return sendCommand(SimpleCommand(input)) { _, answer ->
            answer
        }
    }

    class SimpleCommand(val input: String) : JSLanguageServiceSimpleCommand, JSLanguageServiceObject {
        override fun getCommand() = "accessibility-linter"
        override fun toSerializableObject() = this
    }
}

class ServiceProtocol(project: Project, readyConsumer: Consumer<*>) : JSLanguageServiceNodeStdProtocolBase(project, readyConsumer) {
    override fun createState(): JSLanguageServiceInitialState {
        val result = JSLanguageServiceInitialState()
        result.pluginName = "accessibility-linter"
        val file = JSLanguageServiceUtil.getPluginDirectory(this.javaClass, "lib/index.js")
        result.pluginPath = LocalFilePath.create(file.absolutePath)
        return result
    }

    override fun dispose() {}
}
