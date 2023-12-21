package aoc.year2023

import aoc.Puzzle

/**
 * [Day 15 - Advent of Code 2023](https://adventofcode.com/2023/day/15)
 */
object Day15 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = input.split(',')
        .sumOf { it.map(Char::code).fold(0) { hash, ascii -> ((hash + ascii) * 17).rem(256) }.toInt() }

    override fun solvePartTwo(input: String): Int = TODO()
}
