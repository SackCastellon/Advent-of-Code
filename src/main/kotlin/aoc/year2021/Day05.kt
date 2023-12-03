package aoc.year2021

import aoc.Puzzle
import kotlin.math.max
import kotlin.math.min

/**
 * [Day 5 - Advent of Code 2021](https://adventofcode.com/2021/day/5)
 */
object Day05 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        val map = hashMapOf<Pair<Int, Int>, Int>()

        input.lineSequence()
            .flatMap { line ->
                val (x1, y1, x2, y2) = line.split(" -> ", limit = 2)
                    .flatMap { it.split(",", limit = 2).map(String::toInt) }
                when {
                    x1 == x2 -> (min(y1, y2)..max(y1, y2)).map { x1 to it }
                    y1 == y2 -> (min(x1, x2)..max(x1, x2)).map { it to y1 }
                    else -> emptyList()
                }
            }
            .forEach { map.compute(it) { _, old -> (old ?: 0) + 1 } }

        return map.count { (_, count) -> count >= 2 }
    }

    override fun solvePartTwo(input: String): Int {
        val map = hashMapOf<Pair<Int, Int>, Int>()

        input.lineSequence()
            .flatMap { line ->
                val (x1, y1, x2, y2) = line.split(" -> ", limit = 2)
                    .flatMap { it.split(",", limit = 2).map(String::toInt) }
                when {
                    x1 == x2 -> (min(y1, y2)..max(y1, y2)).map { x1 to it }
                    y1 == y2 -> (min(x1, x2)..max(x1, x2)).map { it to y1 }
                    else -> {
                        val xRange = if (x1 < x2) x1..x2 else x1 downTo x2
                        val yRange = if (y1 < y2) y1..y2 else y1 downTo y2
                        xRange zip yRange
                    }
                }
            }
            .forEach { map.compute(it) { _, old -> (old ?: 0) + 1 } }

        return map.count { (_, count) -> count >= 2 }
    }
}
