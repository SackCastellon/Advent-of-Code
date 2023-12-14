package aoc.year2023

import aoc.Puzzle
import kotlin.math.abs

/**
 * [Day 11 - Advent of Code 2023](https://adventofcode.com/2023/day/11)
 */
object Day11 : Puzzle<Long, Long> {
    override fun solvePartOne(input: String): Long = input.solve(expansionRate = 2)

    override fun solvePartTwo(input: String): Long = input.solve(expansionRate = 1000000)

    private fun String.solve(expansionRate: Int) = lines()
        .map { it.toList() }
        .galaxyPositions()
        .expandRows(expansionRate)
        .expandColumns(expansionRate)
        .cartesianProduct()
        .sumOf(::distanceBetweenPoints)

    private fun List<List<Char>>.galaxyPositions(): List<Pair<Long, Long>> =
        flatMapIndexed { y, line -> line.mapIndexedNotNull { x, cell -> if (cell == '#') x.toLong() to y.toLong() else null } }

    private fun List<Pair<Long, Long>>.expandRows(expansionRate: Int): List<Pair<Long, Long>> {
        var emptyRows = 0
        val sortedBy = sortedBy { it.second }
        val (min, max) = sortedBy.let { it.first().second to it.last().second }
        val galaxiesByRow = sortedBy.groupBy { it.second }

        val result = arrayListOf<Pair<Long, Long>>()

        for (row in min..max) {
            galaxiesByRow[row]?.mapTo(result) { (x, y) -> x to y - emptyRows + emptyRows * expansionRate }
                ?: emptyRows++
        }

        return result
    }

    private fun List<Pair<Long, Long>>.expandColumns(expansionRate: Int): List<Pair<Long, Long>> {
        var emptyColumns = 0
        val sortedBy = sortedBy { it.first }
        val (min, max) = sortedBy.let { it.first().first to it.last().first }
        val galaxiesByColumn = sortedBy.groupBy { it.first }

        val result = arrayListOf<Pair<Long, Long>>()

        for (col in min..max) {
            galaxiesByColumn[col]?.mapTo(result) { (x, y) -> x - emptyColumns + emptyColumns * expansionRate to y }
                ?: emptyColumns++
        }

        return result
    }

    private fun <T> List<T>.cartesianProduct(): List<Pair<T, T>> =
        flatMapIndexed { i, p1 -> drop(i + 1).map { p2 -> p1 to p2 } }

    private fun distanceBetweenPoints(pair: Pair<Pair<Long, Long>, Pair<Long, Long>>): Long {
        val (x1, y1) = pair.first
        val (x2, y2) = pair.second

        return abs(x1 - x2) + abs(y1 - y2)
    }
}
