package io.jpelczar

import io.jpelczar.utils.Reader
import kotlin.math.ceil

fun main() {
    val testLines = Reader("day3/test.txt").asResource {
        it.lines().filter { f -> f.isNotEmpty() }
    }

    val lines = Reader("day3/input.txt").asResource {
        it.lines().filter { f -> f.isNotEmpty() }
    }

    println("--------TEST")
    Day3.partOne(testLines)
    Day3.partTwo(testLines)
    println("--------TASK")
    Day3.partOne(lines)
    Day3.partTwo(lines)
}

object Day3 {
    fun partOne(lines: List<String>) {
        println("Day 3 - Part 1")
        val matrix: List<List<Char>> = prepareMap(lines.map { it.toCharArray().asList() }, 4)

        val treeCounter = countTrees(matrix, 3, 1)
        println("Part 1 tree counter: $treeCounter")
    }

    fun partTwo(lines: List<String>) {
        println("Day 3 - Part 2")
        val steps = listOf(
            Pair(1, 1),
            Pair(3, 1),
            Pair(5, 1),
            Pair(7, 1),
            Pair(1, 2)
        )
        val factor = (steps.map { it.first }.maxOrNull() ?: 1) + 1
        val matrix: List<List<Char>> = prepareMap(lines.map { it.toCharArray().asList() }, factor)
        val trees = mutableListOf<Long>()

        steps.forEach {
            trees.add(countTrees(matrix, it.first, it.second))
        }

        steps.forEachIndexed { index, it -> println("Right ${it.first}, down ${it.second} - trees ${trees[index]}") }
        println("Multiplied ${trees.reduce { acc, i -> acc * i }}")
    }

    private fun countTrees(matrix: List<List<Char>>, columnStep: Int, rowStep: Int): Long {
        var currentColumn = columnStep
        var treeCounter = 0L

        IntRange(rowStep, matrix.size - 1).step(rowStep).forEach {
            if (matrix[it][currentColumn] == '#') {
                treeCounter++
            }
            currentColumn += columnStep
        }

        return treeCounter
    }

    private fun prepareMap(matrix: List<List<Char>>, factorMultiplier: Int): List<List<Char>> {
        val segmentHeight = matrix.size
        val segmentWidth = matrix[0].size

        val factor = ceil(segmentHeight.toFloat() / segmentWidth.toFloat()).toInt() * factorMultiplier

        return matrix.mapIndexed { index, row ->
            var newRow = row
            repeat(factor) { newRow = newRow.plus(matrix[index]) }
            newRow
        }
    }
}
