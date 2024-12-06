package year2024.day6

import utils.InputParser

fun main() {
    val input = InputParser.linesToChar2D("src/main/resources/input2024/input06.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<List<Char>>): String {
    val obstructions = mutableSetOf<Pair<Int, Int>>()
    var guardStart = Pair(-1, -1)
    val rows = input.size
    val cols = input[0].size
    for (r in 0..<rows) {
        for (c in 0..<cols) {
            if (input[r][c] == '#') {
                obstructions.add(Pair(r, c))
            } else if (input[r][c] == '^') {
                guardStart = Pair(r, c)
            }
        }
    }

    val guard = Guard(guardStart, Direction.UP)
    val guardVisits = mutableSetOf<Pair<Int, Int>>()
    while (inBounds(guard.pos, rows, cols)) {
        guardVisits.add(guard.pos)
        var nextPos = guard.nextPos()
        while (nextPos in obstructions) {
            guard.turnRight()
            nextPos = guard.nextPos()
        }
        guard.pos = nextPos
    }
    return guardVisits.size.toString()
}

fun part2(input: List<List<Char>>): String {
    val obstructions = mutableSetOf<Pair<Int, Int>>()
    var guardStart = Pair(-1, -1)
    val rows = input.size
    val cols = input[0].size
    for (r in 0..<rows) {
        for (c in 0..<cols) {
            if (input[r][c] == '#') {
                obstructions.add(Pair(r, c))
            } else if (input[r][c] == '^') {
                guardStart = Pair(r, c)
            }
        }
    }

    val guard = Guard(guardStart, Direction.UP)
    var guardVisitsWithDir: MutableSet<PositionDirection>
    var count = 0
    for (r in 0..<rows) {
        cellLoop@ for (c in 0..<cols) {
            if (obstructions.contains(Pair(r, c)) || Pair(r, c) == guardStart) {
                continue
            }
            obstructions.add(Pair(r, c))
            guard.pos = guardStart
            guard.dir = Direction.UP
            guardVisitsWithDir = mutableSetOf()
            while (inBounds(guard.pos, rows, cols)) {
                guardVisitsWithDir.add(PositionDirection(guard.pos, guard.dir))
                var nextPos = guard.nextPos()
                while (nextPos in obstructions) {
                    guard.turnRight()
                    nextPos = guard.nextPos()
                }
                if (PositionDirection(nextPos, guard.dir) in guardVisitsWithDir) {
                    count++
                    obstructions.remove(Pair(r, c))
                    continue@cellLoop
                }
                guard.pos = nextPos
            }
            obstructions.remove(Pair(r, c))
        }
    }
    return count.toString()
}

fun inBounds(pos: Pair<Int, Int>, rows: Int, cols: Int): Boolean {
    return pos.first in 0..<rows && pos.second in 0..<cols
}

class Guard(var pos: Pair<Int, Int>, var dir: Direction) {
    fun nextPos(): Pair<Int, Int> {
        return when (dir) {
            Direction.RIGHT -> Pair(pos.first, pos.second + 1)
            Direction.LEFT -> Pair(pos.first, pos.second - 1)
            Direction.UP -> Pair(pos.first - 1, pos.second)
            Direction.DOWN -> Pair(pos.first + 1, pos.second)
        }
    }

    fun turnRight() {
        dir = when (dir) {
            Direction.RIGHT -> Direction.DOWN
            Direction.LEFT -> Direction.UP
            Direction.UP -> Direction.RIGHT
            Direction.DOWN -> Direction.LEFT
        }
    }
}

enum class Direction {
    RIGHT, LEFT, UP, DOWN
}

class PositionDirection(val pos: Pair<Int, Int>, val dir: Direction) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PositionDirection

        if (pos != other.pos) return false
        if (dir != other.dir) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pos.hashCode()
        result = 31 * result + dir.hashCode()
        return result
    }
}