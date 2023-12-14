package aoc.year2023

import aoc.Puzzle
import kotlin.math.abs

/**
 * [Day 11 - Advent of Code 2023](https://adventofcode.com/2023/day/11)
 */
object Day11 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = input.lines()
        .map { it.toList() }
        .expandColumns()
        .expandRows()
        .galaxyPositions()
        .cartesianProduct()
        .sumOf(::distanceBetweenPoints)

    override fun solvePartTwo(input: String): Int = TODO()

    private fun List<List<Char>>.expandColumns(): List<List<Char>> {
        val emptyCols = map { it.mapIndexedNotNull { col, cell -> col.takeIf { cell == '.' } }.toSet() }
            .reduce(Set<Int>::intersect)

        return map { it.flatMapIndexed { col, cell -> if (col in emptyCols) listOf(cell, cell) else listOf(cell) } }
    }

    private fun List<List<Char>>.expandRows(): List<List<Char>> =
        flatMap { line -> if (line.all { it == '.' }) listOf(line, line) else listOf(line) }

    private fun List<List<Char>>.galaxyPositions(): List<Pair<Int, Int>> =
        flatMapIndexed { y, line -> line.mapIndexedNotNull { x, cell -> if (cell == '#') x to y else null } }

    private fun <T> List<T>.cartesianProduct(): List<Pair<T, T>> =
        flatMapIndexed { i, p1 -> drop(i + 1).map { p2 -> p1 to p2 } }

    private fun distanceBetweenPoints(pair: Pair<Pair<Int, Int>, Pair<Int, Int>>): Int {
        val (x1, y1) = pair.first
        val (x2, y2) = pair.second

        return abs(x1 - x2) + abs(y1 - y2)
    }
}
