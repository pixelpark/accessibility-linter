package com.github.bucherfa.accessibilitylinter.annotators

class RazorAnnotator : AnnotatorBase() {
    override val fileTypes: List<String>
        get() = listOf("razor")

    override fun prepareInput(input: String): String {
        println("###HTML!!!")
        return input
    }
}
