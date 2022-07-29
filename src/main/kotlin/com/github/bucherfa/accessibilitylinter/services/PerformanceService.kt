package com.github.bucherfa.accessibilitylinter.services

class PerformanceService {
    private val tests: MutableList<Long> = mutableListOf()

    fun add(start: Long, end: Long) {
        tests.add(end - start)
        println("last: ${tests.last()}")
    }

    override fun toString(): String {
        return "${tests.size} tests. average: ${tests.average()}, min: ${tests.minOrNull()}, max: ${tests.maxOrNull()}"
    }
}
