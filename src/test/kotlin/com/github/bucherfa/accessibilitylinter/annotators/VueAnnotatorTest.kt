package com.github.bucherfa.accessibilitylinter.annotators

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class VueAnnotatorTest {
    @Test
    fun prepareInput() {
        val input = VueAnnotator().prepareInput("<template></template>")
        assertEquals("", input)
    }
    @Test
    fun prepareInput2() {
        val input = VueAnnotator().prepareInput("<template></template><script></script><styles</styles>")
        assertEquals("", input)
    }
    @Test
    fun prepareInputTemplateWithinATemplate() {
        val input = VueAnnotator().prepareInput("<template><template></template></template><script></script><styles</styles>")
        assertEquals("<template></template>", input)
    }
    @Test
    fun prepareInput3() {
        val input = VueAnnotator().prepareInput("<template><div :value=\"user.name\" value2=\"user.age\"></template><script></script><styles</styles>")
        assertEquals("<div  value=\"user.name\" value2=\"user.age\">", input)
    }
    @Test
    fun prepareInput4() {
        val input = VueAnnotator().prepareInput("<span></span><template><div></div>")
        assertEquals(null, input)
    }
    @Test
    fun prepareInput5() {
        val input = VueAnnotator().prepareInput("<div></div></template><span></span>")
        assertEquals(null, input)
    }
    @Test
    fun removeColonSyntax() {
        val input = VueAnnotator().removeColonSyntax(":test=")
        assertEquals(" test=", input)
    }
    @Test
    fun removeColonSyntax2() {
        val input = VueAnnotator().removeColonSyntax("<div :value=\"user.name\" value2=\"user.age\">")
        assertEquals("<div  value=\"user.name\" value2=\"user.age\">", input)
    }
    @Test
    fun startingOffset() {
        val annotator = VueAnnotator()
        annotator.prepareInput("<template></template>")
        assertEquals(10, annotator.fileStartingOffset)
    }
    @Test
    fun startingOffset2() {
        val annotator = VueAnnotator()
        annotator.prepareInput("<template><template></template></template><script></script><styles</styles>")
        assertEquals(10, annotator.fileStartingOffset)
    }
    @Test
    fun startingOffset3() {
        val annotator = VueAnnotator()
        annotator.prepareInput("\n<template>\n    <div></div>\n</template>")
        assertEquals(11, annotator.fileStartingOffset)
    }
}
