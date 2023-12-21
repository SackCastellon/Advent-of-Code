package aoc.year2023

import aoc.Puzzle
import kotlin.math.min

/**
 * [Day 13 - Advent of Code 2023](https://adventofcode.com/2023/day/13)
 */
object Day13 : Puzzle<Int, Int> {

    override fun solvePartOne(input: String): Int = input.split("\n\n")
        .map { it.lines().map(String::toList) }
        .sumOf { rows ->
            val linesAbove = getLineOfReflection(rows)
            val linesLeft = getLineOfReflection(rows.transposed())
            linesAbove?.let { it * 100 } ?: linesLeft!!
        }

    override fun solvePartTwo(input: String): Int = input.split("\n\n")
        .map { it.lines().map(String::toList) }
        .sumOf { rows ->
            val linesLeft = getLineOfReflectionWithOneChange(rows.transposed())
            val linesAbove = getLineOfReflectionWithOneChange(rows)
            linesLeft ?: (linesAbove!! * 100)
        }

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

    private fun <T> List<List<T>>.transposed(): List<List<T>> {
        val cols = this[0].size
        val rows = size
        return List(cols) { j -> List(rows) { i -> this[i][j] } }
    }
}
