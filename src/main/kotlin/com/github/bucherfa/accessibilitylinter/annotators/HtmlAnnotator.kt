package com.github.bucherfa.accessibilitylinter.annotators

class HtmlAnnotator : AnnotatorBase() {
    override val fileTypes: List<String>
        get() = listOf("html", "htm")

    override fun prepareInput(input: String): String {
        println("###HTML!!!")
        return input
    }
}
