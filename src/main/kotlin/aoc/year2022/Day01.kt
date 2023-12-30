package aoc.year2022

import aoc.Puzzle

/**
 * [Day 1 - Advent of Code 2022](https://adventofcode.com/2022/day/1)
 */
object Day01 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int =
        input.parse().maxOf { it.lines().sumOf(String::toInt) }

    override fun solvePartTwo(input: String): Int =
        input.parse().map { it.lines().sumOf(String::toInt) }.sortedDescending().take(3).sum()

    private fun String.parse() = split(Regex("(\\r?\\n){2}"))
}
