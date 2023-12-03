package aoc.year2023

import aoc.Puzzle

/**
 * [Day 1 - Advent of Code 2023](https://adventofcode.com/2023/day/1)
 */
object Day01 : Puzzle<Int, Int> {
    private val digitSpellings = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
    )

    override fun solvePartOne(input: String): Int =
        input.lineSequence().sumOf(::getCalibrationValue)

    override fun solvePartTwo(input: String): Int =
        input.lineSequence().map(::processSpelling).sumOf(::getCalibrationValue)

    private fun getCalibrationValue(string: String): Int {
        val firstDigit = string.find(Char::isDigit)!!.digitToInt()
        val lastDigit = string.findLast(Char::isDigit)!!.digitToInt()
        return (firstDigit * 10) + lastDigit
    }

    private fun processSpelling(string: String): String = string.let(::replaceFirstSpelling).let(::replaceLastSpelling)

    private fun replaceFirstSpelling(string: String): String {
        return digitSpellings.mapValues { string.indexOf(it.key) }
            .filter { (_, v) -> v >= 0 }
            .minByOrNull { it.value }
            ?.let { string.replaceFirst(it.key, digitSpellings.getValue(it.key)) }
            ?: string
    }

    private fun replaceLastSpelling(string: String): String {
        return digitSpellings.mapValues { string.lastIndexOf(it.key) }
            .filter { (_, v) -> v >= 0 }
            .maxByOrNull { it.value }
            ?.let { string.replace(it.key, digitSpellings.getValue(it.key)) }
            ?: string
    }
}
