package aoc.year2020

import aoc.Puzzle

/**
 * [Day 1 - Advent of Code 2020](https://adventofcode.com/2020/day/1)
 */
object Day01 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        val set = input.lineSequence().map(String::toInt).toSortedSet()

        val a = set.first { (2020 - it) in set }
        val b = 2020 - a

        return a * b
    }

    override fun solvePartTwo(input: String): Int {
        val set = input.lineSequence().map(String::toInt).toSortedSet()

        set.forEach { a ->
            val subSet = set.subSet(a, 2020 - a)
            subSet.forEach { b ->
                val c = 2020 - a - b
                if (c in subSet)
                    return a * b * c
            }
        }

        throw IllegalStateException()
    }
}
