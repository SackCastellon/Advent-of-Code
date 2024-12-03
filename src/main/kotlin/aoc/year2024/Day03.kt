package aoc.year2024

import aoc.Puzzle

/**
 * [Day 3 - Advent of Code 2024](https://adventofcode.com/2024/day/3)
 */
object Day03 : Puzzle<Int, Int> {

    private val mulRegex = Regex(".*?mul\\((\\d{1,3}),(\\d{1,3})\\)")
    private val doRegex = Regex(".*?do\\(\\)")
    private val dontRegex = Regex(".*?don't\\(\\)")

    override fun solvePartOne(input: String): Int {
        return input.lineSequence()
            .flatMap { line ->
                sequence {
                    var index = 0
                    while (index < line.length) {
                        index = processMul(mulRegex.matchAt(line, index) ?: break)
                    }
                }
            }
            .sum()
    }

    override fun solvePartTwo(input: String): Int {
        var enabled = true
        return input.lineSequence()
            .flatMap { line ->
                sequence {
                    var index = 0
                    while (index < line.length) {
                        if (enabled) {
                            val dontMatch = dontRegex.matchAt(line, index)
                            val mulMatch = mulRegex.matchAt(line, index)

                            if (dontMatch == null) {
                                index = processMul(mulMatch ?: break)
                            } else if (mulMatch == null) {
                                enabled = false
                                index = dontMatch.range.last
                            } else {
                                if (dontMatch.range.last < mulMatch.range.last) {
                                    enabled = false
                                    index = dontMatch.range.last
                                } else {
                                    index = processMul(mulMatch)
                                }
                            }
                        } else {
                            val doMatch = doRegex.matchAt(line, index) ?: break
                            enabled = true
                            index = doMatch.range.last
                        }
                    }
                }
            }
            .sum()
    }

    private suspend fun SequenceScope<Int>.processMul(mulMatch: MatchResult): Int {
        val (a, b) = mulMatch.destructured
        yield(a.toInt() * b.toInt())
        return mulMatch.range.last
    }
}
