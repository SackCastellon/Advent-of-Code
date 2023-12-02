package aoc.year2020

import aoc.Puzzle

/**
 * [Day 4 - Advent of Code 2020](https://adventofcode.com/2020/day/4)
 */
object Day04 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        val reqFields = setOf(
            "byr",
            "iyr",
            "eyr",
            "hgt",
            "hcl",
            "ecl",
            "pid",
            //"cid",
        )

        val deque = ArrayDeque<HashSet<String>>().apply { addLast(hashSetOf()) }

        input.lineSequence().flatMap { it.splitToSequence(' ') }
            .map { it.split(':', limit = 2).first() }
            .forEach {
                if (it.isEmpty()) deque.addLast(HashSet())
                else deque.last().add(it)
            }

        return deque.count { it.containsAll(reqFields) }
    }

    override fun solvePartTwo(input: String): Int {
        val deque = ArrayDeque<MutableList<String>>().apply { addLast(mutableListOf()) }

        input.lineSequence().flatMap { it.splitToSequence(' ') }
            .forEach {
                if (it.isEmpty()) deque.addLast(mutableListOf())
                else deque.last().add(it)
            }

        return deque.count(::isValid)
    }

    private fun isValid(fields: List<String>): Boolean {
        val map = fields.associate { it.split(':', limit = 2).let { (k, v) -> k to v } }

        val byr = map["byr"]
            ?.let { """^([0-9]{4})$""".toRegex().find(it)?.groupValues?.firstOrNull() }
            ?.toIntOrNull()
            ?.let { it in 1920..2002 }
            ?: false

        val iyr = map["iyr"]
            ?.let { """^([0-9]{4})$""".toRegex().find(it)?.groupValues?.firstOrNull() }
            ?.toIntOrNull()
            ?.let { it in 2010..2020 }
            ?: false

        val eyr = map["eyr"]
            ?.let { """^([0-9]{4})$""".toRegex().find(it)?.groupValues?.firstOrNull() }
            ?.toIntOrNull()
            ?.let { it in 2020..2030 }
            ?: false

        val hgt = map["hgt"]
            ?.let { """^(\d+)(cm|in)$""".toRegex().find(it)?.destructured }
            ?.let { (i, unit) ->
                when (unit) {
                    "cm" -> i.toInt() in 150..193
                    "in" -> i.toInt() in 59..76
                    else -> error("Unexpected unit: $unit")
                }
            }
            ?: false

        val hcl = map["hcl"]
            ?.let { """^#([0-9a-f]{6})$""".toRegex().find(it)?.groupValues?.firstOrNull() } != null

        val ecl = map["ecl"]
            ?.let { it in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") }
            ?: false

        val pid = map["pid"]
            ?.let { """^([0-9]{9})$""".toRegex().find(it)?.groupValues?.firstOrNull() } != null

        return byr && iyr && eyr && hgt && hcl && ecl && pid
    }
}