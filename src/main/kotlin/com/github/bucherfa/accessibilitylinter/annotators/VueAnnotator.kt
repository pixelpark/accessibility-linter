package com.github.bucherfa.accessibilitylinter.annotators

class VueAnnotator : AnnotatorBase() {
    override val fileTypes: List<String>
        get() = listOf("vue")

    override fun prepareInput(input: String): String? {
        println("###VueJS!!!")
        val templateStart = "<template>"
        val templateEnd = "</template>"
        val templateStartIndex = input.indexOf(templateStart)
        if (templateStartIndex == -1) {
            return null
        }
        val templateEndIndex = input.indexOf(templateEnd)
        if (templateEndIndex == -1) {
            return null
        }
        if (templateStartIndex > templateEndIndex) {
            return null
        }
        fileStartingOffset = templateStartIndex + templateStart.length
        return input
            .substringAfter(templateStart)
            .substringBeforeLast(templateEnd)
    }
}
