package year2024.day10

import utils.InputParser

fun main() {
    val input = InputParser.linesToChar2D("src/main/resources/input2024/input10.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<List<Char>>): String {
    val trailheads = findTrailheads(input)
    var total = 0
    for (trailhead in trailheads) {
        total += scoreOf(trailhead, input, '0' - 1, mutableSetOf())
    }
    return total.toString()
}

fun part2(input: List<List<Char>>): String {
    val trailheads = findTrailheads(input)
    var total = 0
    for (trailhead in trailheads) {
        total += ratingOf(trailhead, input, '0' - 1)
    }
    return total.toString()
}

fun findTrailheads(map: List<List<Char>>): List<Position> {
    val trailheads = mutableListOf<Position>()
    for (r in map.indices) {
        for (c in map[r].indices) {
            if (map[r][c] == '0') {
                trailheads += Position(r, c)
            }
        }
    }
    return trailheads
}

fun scoreOf(pos: Position, map: List<List<Char>>, prevHeight: Char, reached9s: MutableSet<Position>): Int {
    if (isOutOfBounds(pos, map)) {
        return 0
    }
    val height = map[pos.x][pos.y]
    if (height != prevHeight + 1) {
        return 0
    }
    if (height == '9' && !reached9s.contains(pos)) {
        reached9s += pos
        return 1
    }

    // Add up scores in 4 directions
    val steps = listOf(Position(1, 0), Position(-1, 0), Position(0, 1), Position(0, -1))
    return steps.sumOf { scoreOf(pos + it, map, height, reached9s) }
}

fun ratingOf(pos: Position, map: List<List<Char>>, prevHeight: Char): Int {
    if (isOutOfBounds(pos, map)) {
        return 0
    }
    val height = map[pos.x][pos.y]
    if (height != prevHeight + 1) {
        return 0
    }
    if (height == '9') {
        return 1
    }

    // Add up scores in 4 directions
    val steps = listOf(Position(1, 0), Position(-1, 0), Position(0, 1), Position(0, -1))
    return steps.sumOf { ratingOf(pos + it, map, height) }
}

fun isOutOfBounds(pos: Position, map: List<List<Char>>): Boolean {
    val rows = map.size
    val cols = map[0].size
    return pos.x < 0 || pos.x >= rows || pos.y < 0 || pos.y >= cols
}

class Position(val x: Int, val y: Int) {
    operator fun plus(other: Position): Position {
        return Position(x + other.x, y + other.y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}