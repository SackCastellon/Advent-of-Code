package aoc.year2023

import aoc.Puzzle

private typealias Cell = Pair<Int, Int>

/**
 * [Day 3 - Advent of Code 2023](https://adventofcode.com/2023/day/3)
 */
object Day03 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        val lines = input.lines()

        val (symbolCells, digitCells) = lines.getCells { it != '.' }

        return digitCells.filter { it.adjacentCells.any(symbolCells::contains) }
            .map { it.value.toInt() }
            .sum()
    }

    override fun solvePartTwo(input: String): Int {
        val lines = input.lines()

        val (gearCells, digitCells) = lines.getCells { it == '*' }

        return digitCells.flatMap { it.adjacentCells.filter(gearCells::contains).map { cell -> cell to it.value } }
            .groupBy({ it.first }, { it.second })
            .values
            .filter { it.size == 2 }
            .sumOf { it.map(String::toInt).reduce(Int::times) }
    }

    private fun List<String>.getCells(symbolFilter: (Char) -> Boolean): Pair<Set<Cell>, Map<Cell, String>> {
        val symbolCells = hashSetOf<Cell>()
        val digitCells = hashMapOf<Cell, String>()
        val digits = arrayListOf<Char>()

        fun saveDigitCell(x: Int, y: Int) {
            if (digits.isNotEmpty()) {
                digitCells += (x - digits.size to y) to String(digits.toCharArray())
                digits.clear()
            }
        }

        for ((y, line) in withIndex()) {
            for ((x, char) in line.withIndex()) {
                if (char.isDigit()) {
                    digits += char
                } else {
                    saveDigitCell(x, y)

                    if (symbolFilter(char)) {
                        symbolCells += x to y
                    }
                }
            }

            // Edge case: end-of-line number
            saveDigitCell(line.length, y)
        }

        return symbolCells to digitCells
    }

    private val Map.Entry<Cell, String>.adjacentCells: List<Cell>
        get() = buildList {
            val (x, y) = key
            val length = value.length

            generateSequence(x - 1) { it + 1 }
                .take(length + 2)
                .forEach {
                    add(it to y - 1)
                    add(it to y + 1)
                }

            add(x - 1 to y)
            add(x + length to y)
        }
}
