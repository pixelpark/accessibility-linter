package com.github.bucherfa.accessibilitylinter.annotators

class HandlebarsAnnotator : AnnotatorBase() {
    override val fileTypes: List<String>
        get() = listOf("hbs", "handlebars")

    override fun prepareInput(input: String): String {
        println("###Handlebars!!!")
        return removeMultipleCommentsFromString(
            input,
            listOf(
                Pair("\\{\\{!\\-\\-", "\\-\\-\\}\\}"),
                Pair("\\{\\{!", "\\}\\}"),
            )
        );
    }
}
