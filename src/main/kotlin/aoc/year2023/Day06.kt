package aoc.year2023

import aoc.Puzzle

/**
 * [Day 6 - Advent of Code 2023](https://adventofcode.com/2023/day/6)
 */
object Day06 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int =
        input.lineSequence()
            .map { it.dropWhile { !it.isDigit() }.split(Regex(" +")).map { it.toInt() } }
            .chunked(2) { (a, b) -> a.zip(b) }
            .single()
            .map { (time, dist) -> (0..time).map { speed -> (time - speed) * speed }.count { it > dist } }
            .reduce(Int::times)

    override fun solvePartTwo(input: String): Int = TODO()
}
