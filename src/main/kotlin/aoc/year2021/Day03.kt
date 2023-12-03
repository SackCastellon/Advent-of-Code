package aoc.year2021

import aoc.Puzzle
import aoc.year2021.Day03.Criteria.LEAST_COMMON
import aoc.year2021.Day03.Criteria.MOST_COMMON

/**
 * [Day 3 - Advent of Code 2021](https://adventofcode.com/2021/day/3)
 */
object Day03 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        val lines = input.lines()
        val bitCounts = IntArray(lines.first().length) { 0 }

        lines.forEach { line ->
            line.forEachIndexed { i, c ->
                when (c) {
                    '0' -> bitCounts[i] -= 1
                    '1' -> bitCounts[i] += 1
                }
            }
        }

        val gammaRate = bitCounts.fold(0) { acc, i -> (acc shl 1) or (if (i > 0) 1 else 0) }
        val epsilonRate = gammaRate.inv() and ((1 shl bitCounts.size) - 1)

        return gammaRate * epsilonRate
    }

    override fun solvePartTwo(input: String): Int {
        val values: List<IntArray> = input.lines().map { line -> line.map { it.digitToInt() }.toIntArray() }

        val o2Rate = findRating(values, MOST_COMMON)
        val co2Rate = findRating(values, LEAST_COMMON)

        return o2Rate * co2Rate
    }

    private fun findRating(values: List<IntArray>, criteria: Criteria): Int {
        generateSequence(0) { it + 1 }.fold(values) { acc, i ->
            if (acc.size == 1)
                return acc.single()
                    .fold(0) { int, bit -> (int shl 1) or bit }

            acc.groupBy { it[i] }.let {
                val size0 = it.getValue(0).size
                val size1 = it.getValue(1).size

                if (size0 <= size1) when (criteria) {
                    MOST_COMMON -> it.getValue(1)
                    LEAST_COMMON -> it.getValue(0)
                } else when (criteria) {
                    MOST_COMMON -> it.getValue(0)
                    LEAST_COMMON -> it.getValue(1)
                }
            }
        }
        throw IllegalStateException()
    }

    private enum class Criteria {
        MOST_COMMON,
        LEAST_COMMON
    }
}
