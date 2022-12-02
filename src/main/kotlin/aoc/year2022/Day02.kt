package aoc.year2022

import aoc.Puzzle

/**
 * [Day 2 - Advent of Code 2022](https://adventofcode.com/2022/day/2)
 */
object Day02 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int =
        input.lines().sumOf {
            val (shape, response) = it.split(" ").map(String::toShape)
            (shape vs response).score + response.score
        }

    override fun solvePartTwo(input: String): Int =
        input.lines().sumOf {
            val (left, right) = it.split(" ")
            val result = right.toResult()
            val shape = left.toShape()
            (shape responseFor result).score + result.score
        }
}

private fun String.toShape(): Shape = when (this) {
    "A", "X" -> Shape.Rock
    "B", "Y" -> Shape.Paper
    "C", "Z" -> Shape.Scissors
    else -> throw IllegalArgumentException("Unexpected shape: $this")
}

private fun String.toResult(): Result = when (this) {
    "X" -> Result.Lose
    "Y" -> Result.Draw
    "Z" -> Result.Win
    else -> throw IllegalArgumentException("Unexpected shape: $this")
}

private enum class Shape(val score: Int, defeatedByGetter: () -> Shape, defeatsGetter: () -> Shape) {
    Rock(1, { Paper }, { Scissors }),
    Paper(2, { Scissors }, { Rock }),
    Scissors(3, { Rock }, { Paper });

    private val defeatedBy by lazy(defeatedByGetter)
    private val defeats by lazy(defeatsGetter)

    infix fun vs(shape: Shape): Result = when (shape) {
        defeats -> Result.Lose
        this -> Result.Draw
        else -> Result.Win
    }

    infix fun responseFor(result: Result): Shape = when (result) {
        Result.Lose -> this.defeats
        Result.Draw -> this
        Result.Win -> this.defeatedBy
    }
}

private enum class Result(val score: Int) {
    Lose(0), Draw(3), Win(6)
}
