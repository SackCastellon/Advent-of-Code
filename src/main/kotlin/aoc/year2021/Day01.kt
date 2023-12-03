package aoc.year2021

import aoc.Puzzle

/**
 * [Day 1 - Advent of Code 2021](https://adventofcode.com/2021/day/1)
 */
object Day01 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int =
        input.lineSequence()
            .map(String::toInt)
            .zipWithNext()
            .count { (a, b) -> b > a }

    override fun solvePartTwo(input: String): Int =
        input.lineSequence()
            .map(String::toInt)
            .windowed(size = 4)
            .count { (a, _, _, b) -> b > a }
}
