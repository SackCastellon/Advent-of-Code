package aoc

import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class PuzzleTest {
    private val puzzles: List<TestCase<*, *>> = listOf(
        // Year 2022
        TestCase(aoc.year2022.Day01, answer1 = 65912, answer2 = 195625),
        TestCase(aoc.year2022.Day02, answer1 = 12645, answer2 = 11756),
        TestCase(aoc.year2022.Day03, answer1 = 7766, answer2 = 2415),
        TestCase(aoc.year2022.Day04, answer1 = 644, answer2 = 926),
        TestCase(aoc.year2022.Day05, answer1 = "ZRLJGSCTR", answer2 = "PRTTGRFPB"),
        TestCase(aoc.year2022.Day06, answer1 = 1109, answer2 = 3965),
        TestCase(aoc.year2022.Day08, answer1 = 1669, answer2 = 0),

        // Year 2023
        TestCase(aoc.year2023.Day01, answer1 = 54597, answer2 = 54517),
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
