package aoc.year2023

import aoc.Puzzle

/**
 * [Day 5 - Advent of Code 2023](https://adventofcode.com/2023/day/5)
 */
object Day05 : Puzzle<Long, Long> {
    override fun solvePartOne(input: String): Long {
        val sections = input.split("\n\n")
        val seeds = sections.first().removePrefix("seeds: ").split(' ').map { it.toLong() }

        return sections.drop(1)
            .map { category ->
                category.lines()
                    .drop(1)
                    .map { line ->
                        line.split(' ')
                            .map { it.toLong() }
                            .let { Mapping(it[0], it[1], it[2]) }
                    }
                    .sortedBy { it.srcStart }
            }
            .fold(seeds) { numbers, mappings ->
                numbers.map { number ->
                    val index = mappings.binarySearchBy(number) { it.srcStart }
                        .let { if (it < 0) -it - 2 else it }
                        .coerceAtLeast(0)

                    val (dstStart, srcStart, length) = mappings[index]

                    if (number in srcStart..srcStart + length) {
                        dstStart + number - srcStart
                    } else {
                        number
                    }
                }
            }
            .min()
    }

    override fun solvePartTwo(input: String): Long = TODO()

    private data class Mapping(val dstStart: Long, val srcStart: Long, val length: Long)
}
