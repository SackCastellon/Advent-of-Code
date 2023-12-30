package aoc.year2023

import aoc.Puzzle
import aoc.utils.transposed
import kotlin.math.min

/**
 * [Day 13 - Advent of Code 2023](https://adventofcode.com/2023/day/13)
 */
object Day13 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = solve(input, ::getLineOfReflection)

    override fun solvePartTwo(input: String): Int = solve(input, ::getLineOfReflectionWithOneChange)

    private fun solve(input: String, process: (List<List<Char>>) -> Int?) = input.split(Regex("(\\r?\\n){2}"))
        .map { it.lines().map(String::toList) }
        .sumOf { rows -> process(rows.transposed()) ?: (process(rows)!! * 100) }

    private fun getLineOfReflection(input: List<List<Char>>): Int? =
        (1..input.lastIndex).firstNotNullOfOrNull { index ->
            val size = min(index, input.size - index)
            val firstHalf = input.take(index).takeLast(size)
            val secondHalf = input.drop(index).take(size).asReversed()
            index.takeIf { firstHalf == secondHalf }
        }

    private fun getLineOfReflectionWithOneChange(input: List<List<Char>>): Int? =
        (1..input.lastIndex).firstNotNullOfOrNull { index ->
            val size = min(index, input.size - index)
            val firstHalf = input.take(index).takeLast(size)
            val secondHalf = input.drop(index).take(size).asReversed()
            val changesNeeded = firstHalf.zip(secondHalf).sumOf { (a, b) -> a.zip(b).count { (a, b) -> a != b } }
            index.takeIf { changesNeeded == 1 }
        }
}
