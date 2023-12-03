package aoc.year2015

import aoc.Puzzle

/**
 * [Day 2 - Advent of Code 2015](https://adventofcode.com/2015/day/2)
 */
object Day02 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = input.lineSequence().sumOf { it.getWrappingPaperArea() }

    override fun solvePartTwo(input: String): Int = input.lineSequence().sumOf { it.getRibbonLength() }

    private fun String.getWrappingPaperArea(): Int {
        val (l, w, h) = split('x').map { it.toInt() }
        val area = 2 * l * w + 2 * w * h + 2 * h * l
        val slack = minOf(l * w, w * h, h * l)
        return area + slack
    }

    private fun String.getRibbonLength(): Int {
        val sides = split('x').map { it.toInt() }.sorted()
        val wrapping = sides.take(2).sumOf { it * 2 }
        val bow = sides.reduce(Int::times)
        return wrapping + bow
    }
}
