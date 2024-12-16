package year2024.day15

import utils.InputParser

fun main() {
    val input = InputParser.splitStrings("src/main/resources/input2024/input15.txt", "\n\n")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<String>): String {
    val warehouse = getWarehouse(input[0])
    val moves = input[1].replace("\n", "")

    for (move in moves) {
        when (move) {
            '^' -> warehouse.move(Direction.UP)
            'v' -> warehouse.move(Direction.DOWN)
            '>' -> warehouse.move(Direction.RIGHT)
            '<' -> warehouse.move(Direction.LEFT)
        }
    }
    return warehouse.gpsSum().toString()
}

fun part2(input: List<String>): String {
    return ""
}

fun getWarehouse(input: String): Warehouse {
    val grid = input.split("\n").map { it.toCharArray() }
    var robot = Position(0, 0)
    val walls = mutableSetOf<Position>()
    val boxes = mutableSetOf<Position>()
    for (r in grid.indices) {
        for (c in grid[r].indices) {
            when (grid[r][c]) {
                '@' -> robot = Position(r, c)
                '#' -> walls.add(Position(r, c))
                'O' -> boxes.add(Position(r, c))
            }
        }
    }
    return Warehouse(robot, walls, boxes)
}

fun offsets(dir: Direction): Pair<Int, Int> {
    val offsetRow = when (dir) {
        Direction.UP -> -1
        Direction.DOWN -> 1
        Direction.LEFT -> 0
        Direction.RIGHT -> 0
    }
    val offsetCol = when (dir) {
        Direction.UP -> 0
        Direction.DOWN -> 0
        Direction.LEFT -> -1
        Direction.RIGHT -> 1
    }
    return Pair(offsetRow, offsetCol)
}

class Position(var row: Int, var col: Int) {
    operator fun plus(other: Position): Position {
        return Position(row + other.row, col + other.col)
    }

    override fun toString() = "($row, $col)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (row != other.row) return false
        if (col != other.col) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        return result
    }
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

class Warehouse(var robot: Position, val walls: Set<Position>, val boxes: MutableSet<Position>) {
    fun move(dir: Direction) {
        val (offsetRow, offsetCol) = offsets(dir)

        var openRow = robot.row + offsetRow
        var openCol = robot.col + offsetCol
        while (true) {
            if (Position(openRow, openCol) in walls) {
                return
            }
            if (Position(openRow, openCol) !in boxes) {
                break
            }
            openRow += offsetRow
            openCol += offsetCol
        }

        if (openRow != robot.row + offsetRow || openCol != robot.col + offsetCol) {
            boxes.remove(robot + Position(offsetRow, offsetCol))
            boxes.add(Position(openRow, openCol))
        }
        robot += Position(offsetRow, offsetCol)
    }

    fun gpsSum() = boxes.sumOf { 100L * it.row + it.col }

    override fun toString(): String {
        val rows = walls.maxOf { it.row + 1 }
        val cols = walls.maxOf { it.col + 1 }
        val grid = Array(rows) { Array(cols) {'.'} }

        grid[robot.row][robot.col] = '@'
        for (boxPos in boxes) {
            grid[boxPos.row][boxPos.col] = 'O'
        }
        for (wallPos in walls) {
            grid[wallPos.row][wallPos.col] = '#'
        }

        val sb = StringBuilder()
        for (row in grid) {
            for (c in row) {
                sb.append(c)
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}