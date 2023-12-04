package aoc.year2023

import aoc.Puzzle
import kotlin.math.pow

/**
 * [Day 4 - Advent of Code 2023](https://adventofcode.com/2023/day/4)
 */
object Day04 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = input.lineSequence().map { it.toCard() }.sumOf { it.points }

    override fun solvePartTwo(input: String) = TODO()
}

private fun String.toCard(): Card {
    val (idStr, winningStr, playingStr) = split(':', '|').map { it.trim() }
    val id = idStr.removePrefix("Card ").trim().toInt()
    val winning = winningStr.splitToSequence(' ').filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
    val playing = playingStr.splitToSequence(' ').filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
    return Card(id, winning, playing)
}

private data class Card(val id: Int, val winning: Set<Int>, val playing: Set<Int>) {
    val points = playing.count { it in winning }.let { if (it == 0) 0 else 2.0.pow(it - 1.0).toInt() }
}
