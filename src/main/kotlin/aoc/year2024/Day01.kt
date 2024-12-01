package aoc.year2024

import aoc.Puzzle
import kotlin.math.abs

/**
 * [Day 1 - Advent of Code 2024](https://adventofcode.com/2024/day/1)
 */
object Day01 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        return input.lineSequence()
            .map { line -> line.split(Regex(" +")).map(String::toInt).let { (a, b) -> a to b } }
            .unzip()
            .let { (a, b) -> a.sorted() zip b.sorted() }
            .sumOf { (a, b) -> abs(a - b) }
    }

    override fun solvePartTwo(input: String): Int {
        val left = HashMap<Int, Int>()
        val right = HashMap<Int, Int>()

        input.lineSequence().forEach { line ->
            line.split(Regex(" +")).map(String::toInt).let { (a, b) ->
                left.compute(a) { _, count -> (count ?: 0) + 1 }
                right.compute(b) { _, count -> (count ?: 0) + 1 }
            }
        }

        return left.map { (number, leftCount) ->
            val rightCount = right.getOrDefault(number, 0)
            number * leftCount * rightCount
        }.sum()
    }
}
