package aoc.year2015

import aoc.Puzzle

/**
 * [Day 1 - Advent of Code 2015](https://adventofcode.com/2015/day/1)
 */
object Day01 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = input.sumOf { it.toDirection() }

    override fun solvePartTwo(input: String): Int =
        input.asSequence()
            .map { it.toDirection() }
            .runningReduce(Int::plus)
            .indexOf(-1) + 1

    private fun Char.toDirection() = when (this) {
        '(' -> 1
        ')' -> -1
        else -> error("Unexpected char")
    }
}
