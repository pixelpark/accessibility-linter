package com.github.bucherfa.accessibilitylinter.annotators

import com.google.gson.JsonArray
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class HtmlAnnotatorTest {
    @Test
    fun emptyResult() {
        val list = HtmlAnnotator().processResult(JsonArray())
        assertEquals(0, list.size)
    }
}
