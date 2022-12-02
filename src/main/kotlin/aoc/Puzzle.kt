package aoc

/**
 * Represents a puzzle from the [Advent of Code](https://adventofcode.com)
 */
interface Puzzle<out R1 : Any, out R2 : Any> {
    /**
     * Solve the part one of the puzzle for the given [input]
     */
    fun solvePartOne(input: String): R1

    /**
     * Solve the part two of the puzzle for the given [input]
     */
    fun solvePartTwo(input: String): R2
}
