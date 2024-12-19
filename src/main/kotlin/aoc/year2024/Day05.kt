package aoc.year2024

import aoc.Puzzle

/**
 * [Day 5 - Advent of Code 2024](https://adventofcode.com/2024/day/5)
 */
object Day05 : Puzzle<Int, Int> {

    override fun solvePartOne(input: String): Int {
        val (rules, updates) = input.parseInput()

        return updates.filter { it == it.sortedWith(rules) }
            .sumOf { it[it.lastIndex / 2] }
    }

    override fun solvePartTwo(input: String): Int {
        val (rules, updates) = input.parseInput()

        return updates.mapNotNull { it.sortedWith(rules).takeUnless(it::equals) }
            .sumOf { it[it.lastIndex / 2] }
    }

    private fun String.parseInput(): Pair<Comparator<Int>, Sequence<List<Int>>> = split("\n\n")
        .let { (rules, updates) -> rules.lines().parseRules() to updates.lines().parseUpdates() }

    private fun List<String>.parseRules(): Comparator<Int> {
        val rules = asSequence()
            .takeWhile { it.isNotBlank() }
            .map { it.split('|').map(String::toInt) }
            .groupBy({ it[0] }, { it[1] })
        return Comparator { a, b -> if (b in rules.getOrElse(a, ::listOf)) -1 else 0 }
    }

    private fun List<String>.parseUpdates(): Sequence<List<Int>> = asSequence().map { it.split(',').map(String::toInt) }
}
