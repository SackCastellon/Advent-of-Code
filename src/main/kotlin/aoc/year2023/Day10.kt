package aoc.year2023

import aoc.Puzzle

/**
 * [Day 10 - Advent of Code 2023](https://adventofcode.com/2023/day/10)
 */
object Day10 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        lateinit var start: Point

        val connections = input.lines()
            .flatMapIndexed { y, line ->
                line.mapIndexed { x, pipe ->
                    val p = Point(x, y)
                    when (pipe) {
                        '|' -> p to listOf(p.north(), p.south())
                        '-' -> p to listOf(p.east(), p.west())
                        'L' -> p to listOf(p.north(), p.east())
                        'J' -> p to listOf(p.north(), p.west())
                        '7' -> p to listOf(p.south(), p.west())
                        'F' -> p to listOf(p.south(), p.east())
                        '.' -> p to listOf()
                        'S' -> p to listOf(p.north(), p.south(), p.east(), p.west()).also { start = p }
                        else -> error("Unexpected pipe shape '$pipe'")
                    }
                }
            }
            .toMap()
            .withDefault { listOf() }

        fun getPathThrough(point: Point): Sequence<Point> {
            return generateSequence(start to point) { (prev, curr) ->
                connections.getValue(curr)
                    .single { it != prev && curr in connections.getValue(it) }
                    .let { curr to it }
            }.map { it.second }
        }

        return connections.getValue(start)
            .filter { start in connections.getValue(it) }
            .map(::getPathThrough)
            .let { (a, b) -> a zip b }
            .takeWhile { (a, b) -> a != b }
            .count() + 1
    }

    override fun solvePartTwo(input: String): Int = TODO()

    private data class Point(val x: Int, val y: Int) {
        fun north() = copy(y = y - 1)
        fun south() = copy(y = y + 1)
        fun east() = copy(x = x + 1)
        fun west() = copy(x = x - 1)
    }
}
