package aoc.year2023

import aoc.Puzzle

/**
 * [Day 16 - Advent of Code 2023](https://adventofcode.com/2023/day/16)
 */
object Day16 : Puzzle<Int, Int> {
    override fun solvePartOne(input: String): Int {
        val layout = input.toLayout()
        val beam = Beam(Point(0, 0), Direction.RIGHT)
        return getEnergizedTiles(layout, beam)
    }

    override fun solvePartTwo(input: String): Int {
        val layout = input.toLayout()
        val (lastX, lastY) = input.lines().let { it.first().lastIndex to it.lastIndex }

        return sequence {
            // Top row
            yieldAll((0..lastX).asSequence().map { x -> Beam(Point(x = x, y = 0), Direction.DOWN) })
            // Bottom row
            yieldAll((0..lastX).asSequence().map { x -> Beam(Point(x = x, y = lastY), Direction.UP) })
            // Left-most column
            yieldAll((0..lastY).asSequence().map { y -> Beam(Point(x = 0, y = y), Direction.RIGHT) })
            // Right-most column
            yieldAll((0..lastY).asSequence().map { y -> Beam(Point(x = lastX, y = y), Direction.LEFT) })
        }.maxOf { beam -> getEnergizedTiles(layout, beam) }
    }

    private fun getEnergizedTiles(layout: Map<Point, Component>, start: Beam): Int {
        val existingBeams = hashSetOf<Beam>()

        fun doBounce(beam: Beam) {
            if (beam in existingBeams) return
            val component = layout[beam.point] ?: return
            existingBeams.add(beam)
            component.bounce(beam).forEach(::doBounce)
        }

        doBounce(start)

        return existingBeams.map(Beam::point).distinct().count()
    }

    private fun String.toLayout(): Map<Point, Component> {
        val lines = lines()
        val components = hashMapOf<Point, Component>()
        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, symbol ->
                val point = Point(x, y)
                components[point] = Component.fromSymbol(symbol)
            }
        }
        return components
    }

    private data class Point(val x: Int, val y: Int)

    private enum class Direction {
        UP, DOWN, LEFT, RIGHT,
    }

    private data class Beam(val point: Point, val direction: Direction)

    private enum class Component(private val symbol: Char) {
        EMPTY_SPACE('.') {
            override fun bounce(beam: Beam): List<Beam> {
                val direction = beam.direction
                val point = beam.point
                val nextPoint = when (direction) {
                    Direction.UP -> point.copy(y = point.y - 1)
                    Direction.DOWN -> point.copy(y = point.y + 1)
                    Direction.LEFT -> point.copy(x = point.x - 1)
                    Direction.RIGHT -> point.copy(x = point.x + 1)
                }

                return listOf(beam.copy(point = nextPoint))
            }
        },
        MIRROR_UP('/') {
            override fun bounce(beam: Beam): List<Beam> {
                val direction = beam.direction
                val point = beam.point
                val nextBeam = when (direction) {
                    Direction.UP -> Beam(point.copy(x = point.x + 1), Direction.RIGHT)
                    Direction.DOWN -> Beam(point.copy(x = point.x - 1), Direction.LEFT)
                    Direction.LEFT -> Beam(point.copy(y = point.y + 1), Direction.DOWN)
                    Direction.RIGHT -> Beam(point.copy(y = point.y - 1), Direction.UP)
                }

                return listOf(nextBeam)
            }
        },
        MIRROR_DOWN('\\') {
            override fun bounce(beam: Beam): List<Beam> {
                val direction = beam.direction
                val point = beam.point
                val nextBeam = when (direction) {
                    Direction.UP -> Beam(point.copy(x = point.x - 1), Direction.LEFT)
                    Direction.DOWN -> Beam(point.copy(x = point.x + 1), Direction.RIGHT)
                    Direction.LEFT -> Beam(point.copy(y = point.y - 1), Direction.UP)
                    Direction.RIGHT -> Beam(point.copy(y = point.y + 1), Direction.DOWN)
                }

                return listOf(nextBeam)
            }
        },
        SPLITTER_VERTICAL('|') {
            override fun bounce(beam: Beam): List<Beam> {
                val direction = beam.direction
                val point = beam.point
                return when (direction) {
                    Direction.UP -> listOf(beam.copy(point = point.copy(y = point.y - 1)))
                    Direction.DOWN -> listOf(beam.copy(point = point.copy(y = point.y + 1)))
                    Direction.LEFT, Direction.RIGHT -> listOf(
                        Beam(point.copy(y = point.y - 1), Direction.UP),
                        Beam(point.copy(y = point.y + 1), Direction.DOWN)
                    )
                }
            }
        },
        SPLITTER_HORIZONTAL('-') {
            override fun bounce(beam: Beam): List<Beam> {
                val direction = beam.direction
                val point = beam.point
                return when (direction) {
                    Direction.LEFT -> listOf(beam.copy(point = point.copy(x = point.x - 1)))
                    Direction.RIGHT -> listOf(beam.copy(point = point.copy(x = point.x + 1)))
                    Direction.UP, Direction.DOWN -> listOf(
                        Beam(point.copy(x = point.x - 1), Direction.LEFT),
                        Beam(point.copy(x = point.x + 1), Direction.RIGHT)
                    )
                }
            }
        };

        abstract fun bounce(beam: Beam): List<Beam>

        companion object {
            private val cache = entries.associateBy { it.symbol }

            fun fromSymbol(symbol: Char): Component = cache.getValue(symbol)
        }
    }
}
