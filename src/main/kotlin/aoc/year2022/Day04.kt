package aoc.year2022

import aoc.Puzzle

/**
 * [Day 4 - Advent of Code 2022](https://adventofcode.com/2022/day/4)
 */
object Day04 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int =
        input.lineSequence()
            .count {
                it.split(',')
                    .map { range ->
                        range.split('-')
                            .map(String::toInt)
                            .let { (a, b) -> a..b }
                    }
                    .let { (a, b) -> a in b || b in a }
            }

    override fun solvePartTwo(input: String): Int =
        input.lineSequence()
            .count {
                it.split(',')
                    .map { range ->
                        range.split('-')
                            .map(String::toInt)
                            .let { (a, b) -> a..b }
                    }
                    .let { (a, b) -> a overlaps b || b overlaps a }
            }

    private operator fun IntRange.contains(other: IntRange): Boolean = first in other && last in other
    private infix fun IntRange.overlaps(other: IntRange): Boolean = first in other || last in other
}
