package aoc

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class PuzzleTest {
    private val puzzles: List<TestCase<*, *>> = listOf(
        // Year 2015
        TestCase(aoc.year2015.Day01, answer1 = 138, answer2 = 1771),
        TestCase(aoc.year2015.Day02, answer1 = 1606483, answer2 = 3842356),

        // Year 2020
        TestCase(aoc.year2020.Day01, answer1 = 471019, answer2 = 103927824),
        TestCase(aoc.year2020.Day02, answer1 = 655, answer2 = 673),
        TestCase(aoc.year2020.Day03, answer1 = 289, answer2 = 5522401584),
        TestCase(aoc.year2020.Day04, answer1 = 170, answer2 = 103),

        // Year 2021
        TestCase(aoc.year2021.Day01, answer1 = 1121, answer2 = 1065),
        TestCase(aoc.year2021.Day02, answer1 = 1524750, answer2 = 1592426537),
        TestCase(aoc.year2021.Day03, answer1 = 3901196, answer2 = 4412188),
        TestCase(aoc.year2021.Day04, answer1 = 82440, answer2 = 20774),
        TestCase(aoc.year2021.Day05, answer1 = 5632, answer2 = 22213),
        TestCase(aoc.year2021.Day06, answer1 = 352151, answer2 = 1601616884019),
        TestCase(aoc.year2021.Day07, answer1 = 352331),

        // Year 2022
        TestCase(aoc.year2022.Day01, answer1 = 65912, answer2 = 195625),
        TestCase(aoc.year2022.Day02, answer1 = 12645, answer2 = 11756),
        TestCase(aoc.year2022.Day03, answer1 = 7766, answer2 = 2415),
        TestCase(aoc.year2022.Day04, answer1 = 644, answer2 = 926),
        TestCase(aoc.year2022.Day05, answer1 = "ZRLJGSCTR", answer2 = "PRTTGRFPB"),
        TestCase(aoc.year2022.Day06, answer1 = 1109, answer2 = 3965),
        TestCase(aoc.year2022.Day08, answer1 = 1669),

        // Year 2023
        TestCase(aoc.year2023.Day01, answer1 = 54597, answer2 = 54504),
        TestCase(aoc.year2023.Day02, answer1 = 1853, answer2 = 72706),
        TestCase(aoc.year2023.Day03, answer1 = 559667, answer2 = 86841457),
        TestCase(aoc.year2023.Day04, answer1 = 15268, answer2 = 6283755),
        TestCase(aoc.year2023.Day05, answer1 = 178159714),
        TestCase(aoc.year2023.Day06, answer1 = 252000),
        TestCase(aoc.year2023.Day07, answer1 = 251136060),
        TestCase(aoc.year2023.Day08, answer1 = 14893, answer2 = 10241191004509),
        TestCase(aoc.year2023.Day09, answer1 = 1479011877, answer2 = 973),
        TestCase(aoc.year2023.Day10, answer1 = 6757, answer2 = 523),
        TestCase(aoc.year2023.Day11, answer1 = 9509330, answer2 = 635832237682),
    )

    @TestFactory
    @DisplayName("Advent of Code")
    internal fun adventOfCode(): Iterable<DynamicNode> = puzzles.groupBy { it.year }
        .mapValues { (_, testCases) -> testCases.map(::toDayContainer) }
        .map { (year, containers) -> toYearContainer(year, containers) }

    private fun toYearContainer(year: String, containers: Iterable<DynamicNode>): DynamicContainer =
        dynamicContainer("Year $year", containers)

    private fun toDayContainer(testCase: TestCase<*, *>): DynamicContainer =
        dynamicContainer(
            "Day ${testCase.day}", listOfNotNull(
                dynamicTest("Part 1", testCase::testPartOne).takeIf { testCase.hasPartOne },
                dynamicTest("Part 2", testCase::testPartTwo).takeIf { testCase.hasPartTwo },
            )
        )
}
