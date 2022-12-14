package aoc.year2022

import aoc.Puzzle

private typealias Tree = Pair<Int, Int>

/**
 * [Day 8 - Advent of Code 2022](https://adventofcode.com/2022/day/8)
 */
object Day08 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        val grid = input.lines().map { it.map(Char::digitToInt) }

        val hiddenHorizontal = grid
            .dropLast(1)
            .withIndex()
            .asSequence()
            .drop(1)
            .flatMap { getHidden(it) { row, col -> row to col } }
            .toSet()

        val rows = grid.size
        val cols = grid.first().size

        val hiddenVertical = (0 until cols)
            .asSequence()
            .drop(1)
            .map { col -> IndexedValue(col, (0 until rows).map { row -> grid[row][col] }) }
            .flatMap { getHidden(it) { col, row -> row to col } }
            .toSet()

        val hidden = hiddenHorizontal.intersect(hiddenVertical).size

        return (rows * cols) - hidden
    }

    override fun solvePartTwo(input: String): Int {
        TODO()
    }

    private fun getHidden(
        indexedValue: IndexedValue<Iterable<Int>>,
        treeBuilder: (Int, Int) -> Tree,
    ): Set<Tree> {
        val (index, list) = indexedValue
        var max = list.first()

        val ltr = list.withIndex()
            .asSequence()
            .drop(1)
            .filter { (_, n) -> (n <= max).also { if (!it) max = n } }
            .map { treeBuilder(index, it.index) }
            .toSet()

        max = list.last()

        val rtl = list.withIndex()
            .reversed()
            .asSequence()
            .drop(1)
            .filter { (_, n) -> (n <= max).also { if (!it) max = n } }
            .map { treeBuilder(index, it.index) }
            .toSet()

        return ltr.intersect(rtl)
    }
}
