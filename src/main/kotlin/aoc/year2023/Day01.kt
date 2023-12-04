package aoc.year2023

import aoc.Puzzle

/**
 * [Day 1 - Advent of Code 2023](https://adventofcode.com/2023/day/1)
 */
object Day01 : Puzzle<Int, Int> {
    private val digitMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
    )

    override fun solvePartOne(input: String): Int {
        fun getCalibrationValue(line: String): Int {
            val firstDigit = line.find(Char::isDigit)!!.digitToInt()
            val lastDigit = line.findLast(Char::isDigit)!!.digitToInt()
            return (firstDigit * 10) + lastDigit
        }

        return input.lineSequence().sumOf(::getCalibrationValue)
    }

    override fun solvePartTwo(input: String): Int {
        fun getCalibrationValue(line: String): Int {
            val firstDigit = digitMap.mapKeys { line.indexOf(it.key) }.filterKeys { it >= 0 }.minBy { it.key }.value
            val lastDigit = digitMap.mapKeys { line.lastIndexOf(it.key) }.filterKeys { it >= 0 }.maxBy { it.key }.value
            return (firstDigit * 10) + lastDigit
        }

        return input.lineSequence().sumOf(::getCalibrationValue)
    }
}
