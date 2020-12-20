package io.jpelczar

import io.jpelczar.utils.Reader

val ROWS_RANGE = IntRange(0, 127).toList()
val COLUMNS_RANGE = IntRange(0, 7).toList()

fun main() {
    val lines = Reader("day5/input.txt").asResource {
        it.lines().filter { f -> f.isNotEmpty() }
    }

    Day5.partOne(lines)
    Day5.partTwo(lines)
}

object Day5 {
    fun partOne(lines: List<String>) {
        println("Day 5 - Part 1")
        extractIds(lines)
            .maxOrNull()
            .apply { println(this) }

    }

    fun partTwo(lines: List<String>) {
        println("Day 5 - Part 2")
        extractIds(lines)
            .let {
                val min = it.minOrNull() ?: 0
                val max = it.maxOrNull() ?: 0
                IntRange(min, max).minus(it)
            }
            .apply { println(this) }
    }

    private fun extractIds(lines: List<String>) = lines
        .map { Pair(it.dropLast(3), it.substring(7)) }
        .map { Pair(findRow(it.first), findColumn(it.second)) }
        .map { toId(it) }

    private fun findRow(stepsDef: String, range: List<Int> = ROWS_RANGE): Int {
        val step = stepsDef.firstOrNull() ?: return range[0]
        return when (step) {
            'F'  -> findRow(stepsDef.substring(1), range.subList(0, range.size / 2))
            'B'  -> findRow(stepsDef.substring(1), range.subList(range.size / 2, range.size))
            else -> throw IllegalStateException(stepsDef)
        }
    }

    private fun findColumn(stepsDef: String, range: List<Int> = COLUMNS_RANGE): Int {
        val step = stepsDef.firstOrNull() ?: return range[0]
        return when (step) {
            'L'  -> findColumn(stepsDef.substring(1), range.subList(0, range.size / 2))
            'R'  -> findColumn(stepsDef.substring(1), range.subList(range.size / 2, range.size))
            else -> throw IllegalStateException(stepsDef)
        }
    }

    private fun toId(pair: Pair<Int, Int>) = pair.first * 8 + pair.second
}
