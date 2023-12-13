package aoc.year2023

import aoc.Puzzle

/**
 * [Day 9 - Advent of Code 2023](https://adventofcode.com/2023/day/9)
 */
object Day09 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = input.lineSequence().map(::processLine).sumOf(::getNextValue)

    override fun solvePartTwo(input: String): Int = input.lineSequence().map(::processLine).sumOf(::getPreviousValue)

    private fun processLine(it: String) = it.split(' ').map(String::toInt)

    private fun getNextValue(values: List<Int>): Int {
        if (values.all { it == 0 }) return 0
        return values.last() + getNextValue(values.zipWithNext().map { (a, b) -> b - a })
    }

    private fun getPreviousValue(values: List<Int>): Int {
        if (values.all { it == 0 }) return 0
        return values.first() - getPreviousValue(values.zipWithNext().map { (a, b) -> b - a })
    }
}
