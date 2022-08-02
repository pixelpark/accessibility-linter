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

    @Test
    fun getFileExtensionNormalFile() {
        val fileType = HtmlAnnotator().getFileExtension("index.html")
        assertEquals("html", fileType)
    }
    @Test
    fun getFileExtensionOnlyExtension() {
        val fileType = HtmlAnnotator().getFileExtension(".html")
        assertEquals("html", fileType)
    }
    @Test
    fun getFileExtensionNoSeparator() {
        val fileType = HtmlAnnotator().getFileExtension("html")
        assertEquals("html", fileType)
    }

    @Test
    fun removeCommentsFromStringHandlebars0() {
        val cleanString = HtmlAnnotator().removeElementsFromString("<blink></blink>\n{{! <blink></blink> }}", "\\{\\{!", "\\}\\}")
        assertEquals("<blink></blink>\n" +
                "                      ", cleanString)
    }
    @Test
    fun removeCommentsFromStringHandlebars1() {
        val cleanString = HtmlAnnotator().removeElementsFromString("<blink></blink>\n{{! <blink></blink> }}\n<blink></blink>\n{{! <blink></blink> }}", "\\{\\{!", "\\}\\}")
        assertEquals("<blink></blink>\n" +
                "                      \n" +
                "<blink></blink>\n" +
                "                      ", cleanString)
    }
    @Test
    fun removeCommentsFromStringHandlebars2() {
        val cleanString = HtmlAnnotator().removeElementsFromString("<blink></blink>\n{{!-- <blink></blink> --}}", "\\{\\{!\\-\\-", "\\-\\-\\}\\}")
        assertEquals("<blink></blink>\n" +
                "                          ", cleanString)
    }

    @Test
    fun prepareInputEmptyString() {
        val input = HtmlAnnotator().prepareInput( "")
        assertEquals("", input)
    }
    @Test
    fun prepareInputHtml() {
        val input = HtmlAnnotator().prepareInput("<html></html>")
        assertEquals("<html></html>", input)
    }
}
