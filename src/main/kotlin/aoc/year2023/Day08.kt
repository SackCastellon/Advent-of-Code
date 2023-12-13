package aoc.year2023

import aoc.Puzzle
import kotlin.math.abs

/**
 * [Day 8 - Advent of Code 2023](https://adventofcode.com/2023/day/8)
 */
object Day08 : Puzzle<Int, Long> {
    override fun solvePartOne(input: String): Int {
        val (instructions, network) = processInput(input)

        return instructions.asInfiniteSequence()
            .runningFold("AAA") { node, instruction ->
                val (left, right) = network.getValue(node)
                when (instruction) {
                    'L' -> left
                    'R' -> right
                    else -> error("Unexpected instruction $instruction")
                }
            }
            .takeWhile { it != "ZZZ" }
            .count()
    }

    override fun solvePartTwo(input: String): Long {
        val (instructions, network) = processInput(input)

        fun getSteps(start: String): Int {
            return instructions.asInfiniteSequence()
                .runningFold(start) { node, instruction ->
                    val (left, right) = network.getValue(node)
                    when (instruction) {
                        'L' -> left
                        'R' -> right
                        else -> error("Unexpected instruction $instruction")
                    }
                }
                .takeWhile { !it.endsWith('Z') }
                .count()
        }

        return network.keys.filter { it.endsWith('A') }.map(::getSteps).map(Int::toLong).reduce(::lcm)
    }

    private fun processInput(input: String): Pair<String, Map<String, Pair<String, String>>> {
        val lines = input.lines()
        val instructions = lines.first()

        val network = lines.drop(2).associate {
            val (source, targets) = it.split(" = ")
            val (left, right) = targets.removeSurrounding("(", ")").split(", ")
            source to (left to right)
        }

        return instructions to network
    }

    private fun String.asInfiniteSequence(): Sequence<Char> = sequence { while (true) this.yieldAll(asSequence()) }

    private fun lcm(a: Long, b: Long): Long = abs(a) * (abs(b) / gcd(a, b))

    private tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a.rem(b))
}
