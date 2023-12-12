package aoc.year2023

import aoc.Puzzle

/**
 * [Day 7 - Advent of Code 2023](https://adventofcode.com/2023/day/7)
 */
object Day07 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int = input.lineSequence()
        .map { it.split(' ').let { (a, b) -> a.toHand() to b.toInt() } }
        .sortedByDescending { (hand, _) -> hand }
        .mapIndexed { index, (_, bid) -> bid * (index + 1) }
        .sum()

    override fun solvePartTwo(input: String): Int = TODO()


    private fun String.toHand() = map(Card.labelCache::getValue).let(::Hand)

    private fun typeOf(cards: List<Card>): Type {
        val matchingCount = cards.groupingBy { it }.eachCount().values.sortedDescending()
        return Type.entries.first { type -> matchingCount.zip(type.matchingCount).all { (a, b) -> a == b } }
    }

    private enum class Card(val label: Char) {
        A('A'),
        K('K'),
        Q('Q'),
        J('J'),
        T('T'),
        NINE('9'),
        EIGHT('8'),
        SEVEN('7'),
        SIX('6'),
        FIVE('5'),
        FOUR('4'),
        THREE('3'),
        TWO('2');

        override fun toString(): String = label.toString()

        companion object {
            internal val labelCache = entries.associateBy { it.label }
        }
    }

    private class Hand(private val cards: List<Card>) : Comparable<Hand> {
        private val type: Type = typeOf(cards)

        override fun compareTo(other: Hand): Int = COMPARATOR.compare(this, other)
        override fun toString(): String = "Hand(cards=$cards, type=$type)"

        companion object {
            private val COMPARATOR = compareBy(Hand::type)
                .thenComparing<Card> { it.cards[0] }
                .thenComparing<Card> { it.cards[1] }
                .thenComparing<Card> { it.cards[2] }
                .thenComparing<Card> { it.cards[3] }
                .thenComparing<Card> { it.cards[4] }
        }
    }

    private enum class Type(val matchingCount: List<Int>) {
        FIVE_KIND(listOf(5)),
        FOUR_KIND(listOf(4, 1)),
        FULL_HOUSE(listOf(3, 2)),
        THREE_KIND(listOf(3)),
        TWO_PAIR(listOf(2, 2)),
        ONE_PAIR(listOf(2)),
        HIGH_CARD(listOf(1)),
    }
}
