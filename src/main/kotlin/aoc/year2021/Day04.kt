package aoc.year2021

import aoc.Puzzle

/**
 * [Day 4 - Advent of Code 2021](https://adventofcode.com/2021/day/4)
 */
object Day04 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        val split = input.split(Regex("(\\r?\\n){2}"))

        val boards = split.drop(1)
            .map { it.split(Regex("\\s")).mapNotNull(String::toIntOrNull).associateWithTo(linkedMapOf()) { false } }

        split.first().splitToSequence(",").map(String::toInt).forEach { n ->
            for (b in boards) {
                b.computeIfPresent(n) { _, _ -> true } ?: continue
                val values = b.values.toList()
                if (values.windowed(5, 5).any { row -> row.all { it } } ||
                    (0 until 5).any { col -> (0 until 5).all { row -> values[col + (row * 5)] } }) {
                    return b.filterValues { !it }.keys.sum() * n
                }
            }
        }
        throw IllegalStateException()
    }

    override fun solvePartTwo(input: String): Int {
        val split = input.split(Regex("(\\r?\\n){2}"))

        val boards = split.drop(1)
            .map { it.split(Regex("\\s")).mapNotNull(String::toIntOrNull).associateWithTo(linkedMapOf()) { false } }
            .toMutableList()

        split.first().splitToSequence(",").map(String::toInt).forEach { n ->
            val iter = boards.listIterator()
            while (iter.hasNext()) {
                val b = iter.next()
                b.computeIfPresent(n) { _, _ -> true } ?: continue
                val values = b.values.toList()
                if (values.windowed(5, 5).any { row -> row.all { it } } ||
                    (0 until 5).any { col -> (0 until 5).all { row -> values[col + (row * 5)] } }) {
                    if (boards.size == 1) return b.filterValues { !it }.keys.sum() * n
                    iter.remove()
                }

            }
        }
        throw IllegalStateException()
    }
}
