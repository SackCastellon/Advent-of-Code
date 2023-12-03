package aoc.year2021

import aoc.Puzzle
import kotlin.math.abs

/**
 * [Day 7 - Advent of Code 2021](https://adventofcode.com/2021/day/7)
 */
object Day07 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        val map = input.lineSequence()
            .single()
            .split(",")
            .map { it.toInt() }
            .sorted()
            .let {
                buildMap {
                    (it.first()..it.last()).associateWithTo(this) { 0 }
                    it.groupingBy { it }.eachCountTo(this)
                }
            }

        val cumulative = map.values.runningReduce(Int::plus)
        val threshold = (cumulative.last() + 1) / 2
        val median = cumulative.indexOfFirst { it > threshold } + map.keys.first()
        return map.filterValues { it > 0 }.map { (pos, count) -> abs(pos - median) * count }.sum()
    }

    override fun solvePartTwo(input: String): Int = TODO()
}
