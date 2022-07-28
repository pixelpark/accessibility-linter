package com.github.bucherfa.accessibilitylinter.misc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class ConfigAxe {
    val rules: Map<String, Boolean>?
    val tags: List<String>?

    constructor() {
        this.rules = null
        this.tags = null
    }

    constructor(rules: Map<String, Boolean>?, tags: List<String>?) {
        this.rules = rules
        this.tags = tags
    }

    override fun toString(): String {
        return "rules: ${this.rules}, tags: ${this.tags}"
    }
}
