package aoc.year2021

import aoc.Puzzle

/**
 * [Day 6 - Advent of Code 2021](https://adventofcode.com/2021/day/6)
 */
object Day06 : Puzzle<Long, Long> {
    override fun solvePartOne(input: String): Long = solve(input, 80)

    override fun solvePartTwo(input: String): Long = solve(input, 256)

    private fun solve(input: String, days: Int): Long {
        val map = input
            .lineSequence()
            .single()
            .split(",")
            .map { it.toInt() }
            .groupingBy { it }
            .eachCount()
            .mapValues { (_, count) -> count.toLong() }

        val finalMap = (0 until days).fold(map) { acc, _ ->
            buildMap {
                acc.forEach { (time, amount) ->
                    if (time == 0) {
                        add(6, amount)
                        add(8, amount)
                    } else {
                        add(time - 1, amount)
                    }
                }
            }
        }

        return finalMap.values.sum()
    }

    private fun MutableMap<Int, Long>.add(time: Int, amount: Long) {
        compute(time) { _, old -> (old ?: 0) + amount }
    }
}
