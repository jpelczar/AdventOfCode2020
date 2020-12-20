package io.jpelczar

import io.jpelczar.utils.Reader

fun main() {
    val groups = Reader("day6/input.txt").asResource {
        it.split(Regex("\\n\\n")).filter { f -> f.isNotEmpty() }
    }

    Day6.partOne(groups)
    Day6.partTwo(groups)
}

object Day6 {
    fun partOne(groups: List<String>) {
        println("Day 6 - Part 1")
        groups.map { group ->
            group.lines()
                .filter { it.isNotBlank() }
                .flatMap { it.asIterable() }
                .distinct()
                .size
        }
            .sum()
            .apply { println(this) }
    }

    fun partTwo(groups: List<String>) {
        println("Day 6 - Part 2")
        groups.map { group ->
            group.lines()
                .filter { it.isNotBlank() }
                .map { it.asIterable().toSet() }
                .reduce { acc, set -> set.intersect(acc) }
                .size
        }
            .sum()
            .apply { println(this) }
    }
}
