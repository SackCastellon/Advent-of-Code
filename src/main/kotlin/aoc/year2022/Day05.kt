package aoc.year2022

import aoc.Puzzle
import java.util.*
import kotlin.collections.ArrayDeque

private typealias Rearrange = (stacks: Map<Int, ArrayDeque<Char>>, amount: Int, from: Int, to: Int) -> Unit

/**
 * [Day 5 - Advent of Code 2022](https://adventofcode.com/2022/day/5)
 */
object Day05 : Puzzle<String, String> {
    private val procedureRegex = Regex("move (\\d+) from (\\d+) to (\\d+)")
    override fun solvePartOne(input: String): String = solve(input, ::rearrangeOneByOne)

    override fun solvePartTwo(input: String): String = solve(input, ::rearrangeAllAtOnce)

    private fun solve(input: String, rearrange: Rearrange): String {
        val lines = input.lines()
        val index = lines.indexOfFirst { it.isEmpty() }

        val stackNames = lines[index - 1].windowed(3, 4).map { it.trim().toInt() }
        val stacks: Map<Int, ArrayDeque<Char>> = stackNames.associateWith { ArrayDeque() }

        lines.slice(0 until index - 1).forEach { line ->
            line.windowed(3, 4)
                .map { it.removeSurrounding("[", "]").singleOrNull() }
                .mapIndexed { index, crate -> if (crate != null) stacks.getValue(stackNames[index]).add(crate) }
        }

        lines.slice((index + 1)..lines.lastIndex)
            .mapNotNull(procedureRegex::matchEntire)
            .map { it.destructured.toList().map(String::toInt) }
            .forEach { (amount, from, to) -> rearrange(stacks, amount, from, to) }

        return stacks.values.map(ArrayDeque<Char>::first).joinToString("")
    }

    private fun rearrangeOneByOne(map: Map<Int, ArrayDeque<Char>>, amount: Int, from: Int, to: Int) {
        val toStack = map.getValue(to)
        val fromStack = map.getValue(from)
        repeat(amount) { toStack.addFirst(fromStack.removeFirst()) }
    }

    private fun rearrangeAllAtOnce(map: Map<Int, ArrayDeque<Char>>, amount: Int, from: Int, to: Int) {
        val tmpStack = ArrayDeque<Char>(amount)
        val toStack = map.getValue(to)
        val fromStack = map.getValue(from)
        repeat(amount) { tmpStack.addFirst(fromStack.removeFirst()) }
        tmpStack.forEach(toStack::addFirst)
    }
}
