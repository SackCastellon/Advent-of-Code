package aoc

import aoc.year2022.Day01
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class PuzzleTest {
    private val puzzles: List<TestCase<*, *>> = listOf(
        TestCase(Day01, answer1 = 65912, answer2 = 195625)
    )

    @TestFactory
    internal fun puzzles(): List<DynamicNode> = puzzles.map { testCase ->
        dynamicContainer(
            "Year ${testCase.year}", listOf(
                dynamicContainer(
                    "Day ${testCase.day}", listOf(
                        dynamicTest("Part 1", testCase::testPartOne),
                        dynamicTest("Part 2", testCase::testPartTwo)
                    )
                )
            )
        )
    }
}
