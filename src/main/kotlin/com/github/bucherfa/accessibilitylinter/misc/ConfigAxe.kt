package com.github.bucherfa.accessibilitylinter.misc

class ConfigAxe {
    val rules: Map<String, Boolean>
    val tags: MutableList<String>
    constructor() {
        this.rules = mapOf()
        this.tags = mutableListOf()
    }
    constructor(rules: Map<String, Boolean>, tags: MutableList<String>) {
        this.rules = rules
        this.tags = tags
    }

    override fun toString(): String {
        return "rules: ${this.rules}, tags: ${this.tags}"
    }
}
