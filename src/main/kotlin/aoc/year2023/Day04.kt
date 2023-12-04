package aoc.year2023

import aoc.Puzzle
import kotlin.math.pow

/**
 * [Day 4 - Advent of Code 2023](https://adventofcode.com/2023/day/4)
 */
object Day04 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = input.lineSequence()
        .map { it.substringAfter(':') }
        .map { parseCard(it) }
        .map { (winning, actual) -> actual.count { it in winning } }
        .map { if (it == 1) 1 else 2.0.pow(it - 1.0).toInt() }
        .sum()

    private fun parseCard(card: String): Pair<Set<Int>, Set<Int>> {
        return card.split('|')
            .map { it.splitToSequence(' ').filter { it.isNotBlank() }.map { it.toInt() }.toSet() }
            .let { (a, b) -> a to b }
    }

    override fun solvePartTwo(input: String) = TODO()
}
