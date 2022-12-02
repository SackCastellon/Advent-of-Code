package aoc

import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class PuzzleTest {
    private val puzzles: List<TestCase<*, *>> = listOf()

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
