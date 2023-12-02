package aoc.year2020

import aoc.Puzzle

/**
 * [Day 3 - Advent of Code 2020](https://adventofcode.com/2020/day/3)
 */
object Day03 : Puzzle<Long, Long> {
    override fun solvePartOne(input: String): Long =
        input.lineSequence().foldIndexed(0) { y, count, line ->
            if (line[(y * 3) % line.length] == '#') count + 1 else count
        }

    override fun solvePartTwo(input: String): Long {
        val slopes = listOf(
            1 to 1,
            3 to 1,
            5 to 1,
            7 to 1,
            1 to 2
        )

        val treeCount = slopes.associateWithTo(mutableMapOf()) { 0L }

        fun check(y: Int, line: String) {
            treeCount.replaceAll { (r, d), count ->
                val i = ((y / d) * r) % line.length
                if (((y % d) == 0) && (line[i] == '#')) count + 1 else count
            }
        }

        input.lineSequence().forEachIndexed(::check)

        return treeCount.values.reduce(Long::times)
    }

}