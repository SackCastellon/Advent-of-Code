package aoc.year2024

import aoc.Puzzle
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * [Day 2 - Advent of Code 2024](https://adventofcode.com/2024/day/2)
 */
object Day02 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        return input.lineSequence()
            .map { it.split(' ').map(String::toInt) }
            .count(::isSafe)
    }

    override fun solvePartTwo(input: String): Int {
        TODO("Not yet implemented")
    }

    private fun isSafe(report: List<Int>): Boolean {
        val groupBy = report.zipWithNext(Int::minus).groupBy { it.sign }
        return groupBy.size == 1 && groupBy.values.single().all { it.absoluteValue in 1..3 }
    }
}
