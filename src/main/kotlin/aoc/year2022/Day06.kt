package aoc.year2022

import aoc.Puzzle

/**
 * [Day 6 - Advent of Code 2022](https://adventofcode.com/2022/day/6)
 */
object Day06 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = detect(input, 4)

    override fun solvePartTwo(input: String): Int = detect(input, 14)

    private fun detect(input: String, size: Int): Int =
        input.windowedSequence(size).indexOfFirst { it.toSet().size == size } + size
}
