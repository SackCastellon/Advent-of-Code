package aoc.year2022

import aoc.Puzzle

/**
 * [Day 3 - Advent of Code 2022](https://adventofcode.com/2022/day/3)
 */
object Day03 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int =
        input.lineSequence()
            .map { it.chunked(it.length / 2) }
            .sumOf { it.sumOfIntersectingPriorities() }

    override fun solvePartTwo(input: String): Int =
        input.lineSequence()
            .chunked(3)
            .sumOf { it.sumOfIntersectingPriorities() }

    private fun List<String>.sumOfIntersectingPriorities(): Int =
        map(String::toSet)
            .reduce(Set<Char>::intersect)
            .sumOf { it.priority }

    private val Char.priority: Int
        get() = when (this) {
            in 'a'..'z' -> code - 'a'.code + 1
            in 'A'..'Z' -> code - 'A'.code + 27
            else -> throw IllegalStateException("Unexpected character: $this")
        }
}
