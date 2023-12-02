package aoc.year2020

import aoc.Puzzle

/**
 * [Day 2 - Advent of Code 2020](https://adventofcode.com/2020/day/2)
 */
object Day02 : Puzzle<Int, Int> {
    private val regex = """(\d+)-(\d+) ([a-z]): ([a-z]+)""".toRegex()

    override fun solvePartOne(input: String): Int =
        input.lineSequence().count { line ->
            val groups = regex.find(line)!!.groupValues
            val min = groups[1].toInt()
            val max = groups[2].toInt()
            val char = groups[3].single()
            val password = groups[4]
            password.count { it == char } in min..max
        }

    override fun solvePartTwo(input: String): Int =
        input.lineSequence().count { line ->
            val groups = regex.find(line)!!.groupValues
            val posA = groups[1].toInt() - 1
            val posB = groups[2].toInt() - 1
            val char = groups[3].single()
            val password = groups[4]
            (password[posA] == char) xor (password[posB] == char)
        }
}