package io.jpelczar

import io.jpelczar.utils.Reader
import kotlin.math.max

fun main() {
    val lines = Reader("day2/input.txt").asResource {
        it.lines().filter { f -> f.isNotEmpty() }
    }

    Day2.partOne(lines)
    Day2.partTwo(lines)
}

object Day2 {
    fun partOne(lines: List<String>) {
        println("Day 2 - Part 1")
        lines
            .map { parseLine(it) }
            .filter { isValidByRange(it) }
            .size
            .apply { println("Result: $this") }
    }

    fun partTwo(lines: List<String>) {
        println("Day 2 - Part 2")
        lines
            .map { parseLine(it) }
            .filter { isValidByPosition(it) }
            .size
            .apply { println("Result: $this") }
    }

    private fun parseLine(line: String): PassInput {
        val split = line.split(Regex("\\s"))
        val rangeRaw = split[0].split("-").map { it.toInt() }

        return PassInput(
            PassPolicy(rangeRaw[0], rangeRaw[1], split[1].toCharArray()[0]),
            split[2]
        )
    }

    private fun isValidByRange(passInput: PassInput): Boolean {
        val times = passInput.password.filter { it == passInput.policy.letter }.length
        return IntRange(passInput.policy.first, passInput.policy.second).contains(times)
    }

    private fun isValidByPosition(passInput: PassInput): Boolean {
        val indexFirst = max(passInput.policy.first - 1, 0)
        val indexSecond = max(passInput.policy.second - 1, 0)
        if (passInput.password.length <= indexFirst || passInput.password.length <= indexSecond) {
            return false
        }
        val lettersByPosition = setOf(passInput.password[indexFirst], passInput.password[indexSecond])

        return lettersByPosition.size == 2 && lettersByPosition.contains(passInput.policy.letter)
    }

    data class PassPolicy(val first: Int, val second: Int, val letter: Char)
    data class PassInput(val policy: PassPolicy, val password: String)
}

/*--- Day 2: Password Philosophy ---
Your flight departs in a few days from the coastal airport; the easiest way down to the coast from here is via toboggan.

The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day. "Something's wrong with our computers; we can't log in!" You ask if you can take a look.

Their password database seems to be a little corrupted: some of the passwords wouldn't have been allowed by the Official Toboggan Corporate Policy that was in effect when they were chosen.

To try to debug the problem, they have created a list (your puzzle input) of passwords (according to the corrupted database) and the corporate policy when that password was set.

For example, suppose you have the following list:

1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc
Each line gives the password policy and then the password. The password policy indicates the lowest and highest number of times a given letter must appear for the password to be valid. For example, 1-3 a means that the password must contain a at least 1 time and at most 3 times.

In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b, but needs at least 1. The first and third passwords are valid: they contain one a or nine c, both within the limits of their respective policies.

How many passwords are valid according to their policies?

Your puzzle answer was 434.

The first half of this puzzle is complete! It provides one gold star: *

--- Part Two ---
While it appears you validated the passwords correctly, they don't seem to be what the Official Toboggan Corporate Authentication System is expecting.

The shopkeeper suddenly realizes that he just accidentally explained the password policy rules from his old job at the sled rental place down the street! The Official Toboggan Corporate Policy actually works a little differently.

Each policy actually describes two positions in the password, where 1 means the first character, 2 means the second character, and so on. (Be careful; Toboggan Corporate Policies have no concept of "index zero"!) Exactly one of these positions must contain the given letter. Other occurrences of the letter are irrelevant for the purposes of policy enforcement.

Given the same example list from above:

1-3 a: abcde is valid: position 1 contains a and position 3 does not.
1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
How many passwords are valid according to the new interpretation of the policies?*/
