package aoc.year2023

import aoc.Puzzle

/**
 * [Day 10 - Advent of Code 2023](https://adventofcode.com/2023/day/10)
 */
object Day10 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        val (connectionMap, start) = getConnectionMap(input)

        return connectionMap.getValue(start)
            .filter { start in connectionMap.getValue(it) }
            .map { getPath(start to it, connectionMap) }
            .let { (a, b) -> a zip b }
            .takeWhile { (a, b) -> a != b }
            .count() + 1
    }

    override fun solvePartTwo(input: String): Int {
        val (connectionMap, start) = getConnectionMap(input)

        val loopPoints = connectionMap.getValue(start)
            .first { start in connectionMap.getValue(it) }
            .let { getPath(start to it, connectionMap) }
            .takeWhile { it != start }
            .toSet() + start

        val loopConnectionMap = connectionMap.filterKeys { it in loopPoints }.withDefault { listOf() }

        val cleanField = input.cleanField(loopConnectionMap)

        return cleanField.sumOf { it.getContainedTiles() }
    }

    private fun getConnectionMap(input: String): Pair<Map<Point, List<Point>>, Point> {
        lateinit var start: Point
        return input.lines()
            .flatMapIndexed { y, line ->
                line.mapIndexed { x, pipe ->
                    val p = Point(x, y)
                    when (pipe) {
                        '|' -> p to listOf(p.north(), p.south())
                        '-' -> p to listOf(p.east(), p.west())
                        'L' -> p to listOf(p.north(), p.east())
                        'J' -> p to listOf(p.north(), p.west())
                        '7' -> p to listOf(p.south(), p.west())
                        'F' -> p to listOf(p.south(), p.east())
                        '.' -> p to listOf()
                        'S' -> p to listOf(p.north(), p.south(), p.east(), p.west()).also { start = p }
                        else -> error("Unexpected pipe shape '$pipe'")
                    }
                }
            }
            .toMap()
            .withDefault { listOf() }
            .let { it to start }
    }

    private fun String.cleanField(connectionMap: Map<Point, List<Point>>): List<List<Char>> {
        return lines().mapIndexed { y, line ->
            line.mapIndexed { x, pipe ->
                val point = Point(x, y)
                when (val size = connectionMap.getValue(point).size) {
                    0 -> '.'
                    2 -> pipe
                    4 -> {
                        point.getPipe(connectionMap)
                    }

                    else -> error("Unexpected number of connections $size")
                }
            }
        }
    }

    private fun Point.getPipe(connectionMap: Map<Point, List<Point>>): Char {
        val startConnections = connectionMap.getValue(this)
            .filter { connectionMap.getValue(it).any(::equals) }
            .toSortedSet()

        return when (startConnections) {
            sortedSetOf(north(), south()) -> '|'
            sortedSetOf(east(), west()) -> '-'
            sortedSetOf(north(), east()) -> 'L'
            sortedSetOf(north(), west()) -> 'J'
            sortedSetOf(south(), west()) -> '7'
            sortedSetOf(south(), east()) -> 'F'
            else -> error(startConnections)
        }
    }

    private fun List<Char>.getContainedTiles(): Int {
        var inside = false
        var lastCorner: Char? = null
        var count = 0
        for (pipe in this) {
            if (pipe in setOf('|', 'L', 'J', '7', 'F')) inside = !inside

            when (pipe) {
                'L', 'F' -> lastCorner = pipe
                '7' -> {
                    if (lastCorner == 'L') inside = !inside
                    lastCorner = null
                }

                'J' -> {
                    if (lastCorner == 'F') inside = !inside
                    lastCorner = null
                }
            }

            if (pipe == '.' && inside) count++
        }
        return count
    }

    private fun getPath(direction: Pair<Point, Point>, connections: Map<Point, List<Point>>): Sequence<Point> {
        return generateSequence(direction) { (prev, curr) ->
            connections.getValue(curr)
                .single { it != prev && curr in connections.getValue(it) }
                .let { curr to it }
        }.map { it.second }
    }

    private data class Point(val x: Int, val y: Int) : Comparable<Point> {
        fun north() = copy(y = y - 1)
        fun south() = copy(y = y + 1)
        fun east() = copy(x = x + 1)
        fun west() = copy(x = x - 1)

        override fun compareTo(other: Point): Int = comparator.compare(this, other)

        companion object {
            private val comparator = Comparator.comparingInt(Point::x).thenComparingInt(Point::y)
        }
    }
}
