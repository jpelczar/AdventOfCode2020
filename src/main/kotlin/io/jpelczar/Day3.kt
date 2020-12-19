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

/*--- Day 3: Toboggan Trajectory ---
With the toboggan login problems resolved, you set off toward the airport. While travel by toboggan might be easy, it's certainly not safe: there's very minimal steering and the area is covered in trees. You'll need to see which angles will take you near the fewest trees.

Due to the local geology, trees in this area only grow on exact integer coordinates in a grid. You make a map (your puzzle input) of the open squares (.) and trees (#) you can see. For example:

..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#
These aren't the only trees, though; due to something you read about once involving arboreal genetics and biome stability, the same pattern repeats to the right many times:

..##.........##.........##.........##.........##.........##.......  --->
#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....  --->
.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........#.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...##....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
You start on the open square (.) in the top-left corner and need to reach the bottom (below the bottom-most row on your map).

The toboggan can only follow a few specific slopes (you opted for a cheaper model that prefers rational numbers); start by counting all the trees you would encounter for the slope right 3, down 1:

From your starting position at the top-left, check the position that is right 3 and down 1. Then, check the position that is right 3 and down 1 from there, and so on until you go past the bottom of the map.

The locations you'd check in the above example are marked here with O where there was an open square and X where there was a tree:

..##.........##.........##.........##.........##.........##.......  --->
#..O#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....X..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#O#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..X...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.X#.......#.##.......#.##.......#.##.......#.##.....  --->
.#.#.#....#.#.#.#.O..#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........X.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.X#...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...#X....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...X.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
In this example, traversing the map using this slope would cause you to encounter 7 trees.

Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you encounter?

Your puzzle answer was 272.

--- Part Two ---
Time to check the rest of the slopes - you need to minimize the probability of a sudden arboreal stop, after all.

Determine the number of trees you would encounter if, for each of the following slopes, you start at the top-left corner and traverse the map all the way to the bottom:

Right 1, down 1.
Right 3, down 1. (This is the slope you already checked.)
Right 5, down 1.
Right 7, down 1.
Right 1, down 2.
In the above example, these slopes would find 2, 7, 3, 4, and 2 tree(s) respectively; multiplied together, these produce the answer 336.

What do you get if you multiply together the number of trees encountered on each of the listed slopes?

Your puzzle answer was 3898725600.*/
