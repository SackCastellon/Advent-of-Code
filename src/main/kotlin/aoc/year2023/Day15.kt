package aoc.year2023

import aoc.Puzzle

/**
 * [Day 15 - Advent of Code 2023](https://adventofcode.com/2023/day/15)
 */
object Day15 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = input.split(',')
        .sumOf { it.map(Char::code).fold(0) { hash, ascii -> ((hash + ascii) * 17).rem(256) }.toInt() }

    override fun solvePartTwo(input: String): Int {
        val hashMap = hashMapOf<Int, MutableList<Lens>>()

        input.split(',').map { it.toStep() }.forEach { step ->
            val box = hashMap.getOrPut(step.hash, ::arrayListOf)
            when (step) {
                is Step.Remove -> box.removeAll { it.label == step.label }
                is Step.Add -> {
                    val index = box.indexOfFirst { it.label == step.label }
                    val lens = Lens(step.label, step.focalLength)
                    if (index == -1) box.add(lens) else box[index] = lens
                }
            }
        }

        return hashMap.map { (box, lenses) ->
            lenses.mapIndexed { slot, lens ->
                (box + 1) * (slot + 1) * lens.focalLength
            }.sum()
        }.sum()
    }

    private fun String.toStep(): Step = if (endsWith('-')) Step.Remove(this) else Step.Add(this)

    private sealed class Step(string: String) {
        val label: String = string.takeWhile { it.isLetter() }
        val hash: Int = label.map(Char::code).fold(0) { hash, ascii -> ((hash + ascii) * 17).rem(256) }

        class Add(string: String) : Step(string) {
            val focalLength: Int = string.dropWhile { !it.isDigit() }.toInt()
        }

        class Remove(string: String) : Step(string)
    }

    private data class Lens(val label: String, val focalLength: Int)
}
