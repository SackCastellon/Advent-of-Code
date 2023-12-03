package aoc

import org.junit.jupiter.api.Assertions.assertEquals

internal data class TestCase<R1 : Any, R2 : Any>(
    private val puzzle: Puzzle<R1, R2>,
    private val answer1: R1? = null,
    private val answer2: R2? = null,
) {
    val year: String by lazy { puzzle.javaClass.packageName.substringAfterLast('.').removePrefix("year") }
    val day: String by lazy { puzzle.javaClass.simpleName.removePrefix("Day") }

    private val input by lazy {
        val pkg = puzzle.javaClass.packageName.replace('.', '/')
        ClassLoader.getSystemClassLoader().getResourceAsStream("$pkg/input$day.txt")!!.bufferedReader().readText()
    }

    val hasPartOne = answer1 != null
    val hasPartTwo = answer2 != null

    fun testPartOne() = assertEquals(answer1, puzzle.solvePartOne(input))
    fun testPartTwo() = assertEquals(answer2, puzzle.solvePartTwo(input))
}
