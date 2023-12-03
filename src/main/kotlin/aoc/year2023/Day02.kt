package aoc.year2023

import aoc.Puzzle

/**
 * [Day 2 - Advent of Code 2023](https://adventofcode.com/2023/day/2)
 */
object Day02 : Puzzle<Int, Int> {
    private val bagContents = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14,
    )

    override fun solvePartOne(input: String): Int {
        fun getValidGameId(string: String): Int? {
            val (game, subsets) = string.split(':')
            val gameId = game.removePrefix("Game ").toInt()
            val isValid = subsets.splitToSequence(';', ',')
                .map(String::trim)
                .map { it.split(' ') }
                .map { (a, b) -> a to b }
                .groupBy({ it.second }, { it.first.toInt() })
                .all { (color, counts) ->
                    val max = bagContents.getValue(color)
                    counts.all { it <= max }
                }

            return gameId.takeIf { isValid }
        }

        return input.lineSequence().mapNotNull(::getValidGameId).sum()
    }

    override fun solvePartTwo(input: String): Int {
        fun getPowerOfMinimumSet(string: String): Int =
            string.substringAfter(':')
                .splitToSequence(';', ',')
                .map(String::trim)
                .map { it.substringBefore(' ') to it.substringAfterLast(' ') }
                .groupBy({ it.second }, { it.first.toInt() })
                .mapValues { it.value.max() }
                .values
                .reduce(Int::times)

        return input.lineSequence().map(::getPowerOfMinimumSet).sum()
    }
}
