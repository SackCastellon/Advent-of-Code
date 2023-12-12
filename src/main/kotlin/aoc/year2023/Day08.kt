package aoc.year2023

import aoc.Puzzle

/**
 * [Day 8 - Advent of Code 2023](https://adventofcode.com/2023/day/8)
 */
object Day08 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        val lines = input.lines()
        val instructions = lines.first()

        val leftNodes = hashMapOf<String, String>()
        val rightNodes = hashMapOf<String, String>()

        lines.drop(2).forEach {
            val (source, targets) = it.split(" = ")
            val (left, right) = targets.removeSurrounding("(", ")").split(", ")
            leftNodes[source] = left
            rightNodes[source] = right
        }

        return sequence { while (true) yieldAll(instructions.asSequence()) }
            .runningFold("AAA") { node, instruction ->
                when (instruction) {
                    'L' -> leftNodes.getValue(node)
                    'R' -> rightNodes.getValue(node)
                    else -> error("Unexpected instruction $instruction")
                }
            }
            .takeWhile { it != "ZZZ" }
            .fold(0) { acc, _ -> acc + 1 }
    }

    override fun solvePartTwo(input: String): Int = TODO()
}
