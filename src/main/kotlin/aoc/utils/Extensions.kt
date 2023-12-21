package aoc.utils

fun <T> List<List<T>>.transposed(): List<List<T>> {
    return List(first().size) { col -> List(size) { row -> this[row][col] } }
}
