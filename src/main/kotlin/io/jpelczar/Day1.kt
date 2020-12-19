package io.jpelczar

import io.jpelczar.utils.Reader

const val MAX_NESTED = 2
const val SUM_VALUE = 2020

fun main() {
    val lines = Reader("day1/input.txt").asResource {
        it.lines().filter { f -> f.isNotEmpty() }.map { line -> line.toInt() }
    }

    partOne(lines)
    partTwo(lines)
}

fun partOne(lines: List<Int>) {
    println("Day 1 - Part 1")
    run loop@{
        lines.forEach { outValue ->
            lines.forEach { inValue ->
                if (outValue + inValue == SUM_VALUE) {
                    println("$outValue + $inValue = 2020")
                    println("$outValue * $inValue = ${inValue * outValue}")
                    return@loop
                }
            }
        }
    }
}

fun partTwo(lines: List<Int>) {
    println("Day 1 - Part 2")
    val result = mutableSetOf<Int>()
    run loop@{
        lines.forEach {
            findNumbers(it, lines).forEach { n ->
                if (n.sum() == SUM_VALUE) {
                    result.addAll(n)
                    return@loop
                }
            }
        }
    }

    println("Sum of $result = ${result.sum()}")
    println("Multiplication of $result = ${result.reduce { acc, i -> acc * i }}")
}

fun findNumbers(value: Int, numbers: List<Int>, nestedLevel: Int = 0): List<List<Int>> {
    if (nestedLevel >= MAX_NESTED) {
        return listOf(listOf(value))
    }
    return numbers.flatMap {
        findNumbers(it, numbers, nestedLevel + 1).map { v -> v.plus(value) }
    }
}

/*--- Day 1: Report Repair ---
After saving Christmas five years in a row, you've decided to take a vacation at a nice resort on a tropical island. Surely, Christmas will go on without you.

The tropical island has its own currency and is entirely cash-only. The gold coins used there have a little picture of a starfish; the locals just call them stars. None of the currency exchanges seem to have heard of them, but somehow, you'll need to find fifty of these coins by the time you arrive so you can pay the deposit on your room.

To save your vacation, you need to get all fifty stars by December 25th.

Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!

Before you leave, the Elves in accounting just need you to fix your expense report (your puzzle input); apparently, something isn't quite adding up.

Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.

For example, suppose your expense report contained the following:

1721
979
366
299
675
1456
In this list, the two entries that sum to 2020 are 1721 and 299. Multiplying them together produces 1721 * 299 = 514579, so the correct answer is 514579.

Of course, your expense report is much larger. Find the two entries that sum to 2020; what do you get if you multiply them together?

Your puzzle answer was 1013211.

The first half of this puzzle is complete! It provides one gold star: *

--- Part Two ---
The Elves in accounting are thankful for your help; one of them even offers you a starfish coin they had left over from a past vacation. They offer you a second one if you can find three numbers in your expense report that meet the same criteria.

Using the above example again, the three entries that sum to 2020 are 979, 366, and 675. Multiplying them together produces the answer, 241861950.

In your expense report, what is the product of the three entries that sum to 2020?

*/
