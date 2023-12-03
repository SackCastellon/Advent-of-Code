package aoc.year2021

import aoc.Puzzle

/**
 * [Day 2 - Advent of Code 2021](https://adventofcode.com/2021/day/2)
 */
object Day02 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        class Submarine1 : Submarine() {
            override fun down(x: Int) {
                depth += x
            }

            override fun up(x: Int) {
                depth -= x
            }

            override fun forward(x: Int) {
                hPos += x
            }
        }
        return solve(input, Submarine1())
    }

    override fun solvePartTwo(input: String): Int {
        class Submarine2 : Submarine() {
            private var aim = 0

            override fun down(x: Int) {
                aim += x
            }

            override fun up(x: Int) {
                aim -= x
            }

            override fun forward(x: Int) {
                hPos += x
                depth += aim * x
            }
        }
        return solve(input, Submarine2())
    }

    private fun solve(input: String, submarine: Submarine): Int =
        submarine.apply {
            input.lineSequence().forEach { line ->
                val (dir, sDelta) = line.split(' ', limit = 2)
                val delta = sDelta.toInt()
                when (dir) {
                    "forward" -> forward(delta)
                    "down" -> down(delta)
                    "up" -> up(delta)
                    else -> error("Unexpected direction")
                }
            }
        }.product

    private abstract class Submarine {
        protected var depth = 0
        protected var hPos = 0
        val product: Int get() = hPos * depth

        abstract fun forward(x: Int)
        abstract fun down(x: Int)
        abstract fun up(x: Int)
    }
}
