package aoc.year2023

import aoc.Puzzle
import aoc.utils.transposed

/**
 * [Day 14 - Advent of Code 2023](https://adventofcode.com/2023/day/14)
 */
object Day14 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = input.lines()
        .map(String::toList)
        .transposed()
        .map { column ->
            column.split('#')
                .map { it.toMutableList() }
                .onEach { it.sortDescending() }
                .reduce { acc, chars -> acc.apply { add('#'); addAll(chars) } }
                .asReversed()
        }
        .sumOf { it.mapIndexedNotNull { index, char -> (index + 1).takeIf { char == 'O' } }.sum() }

    override fun solvePartTwo(input: String): Int = TODO()

    private fun <T> List<T>.split(delimiter: T): List<List<T>> =
        mapIndexedNotNullTo(sortedSetOf(-1, size)) { index, element -> index.takeIf { element == delimiter } }
            .zipWithNext { fromIndex, toIndex -> subList(fromIndex + 1, toIndex) }
}
