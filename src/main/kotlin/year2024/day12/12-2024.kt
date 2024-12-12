package year2024.day12

import utils.InputParser

fun main() {
    val input = InputParser.linesToChar2D("src/main/resources/input2024/input12.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<List<Char>>): String {
    val regions = getRegions(input)
    return regions.sumOf { it.area() * it.perimeter(input) }.toString()
}

fun part2(input: List<List<Char>>): String {
    val regions = getRegions(input)
    return regions.sumOf { it.area() * it.numCorners(input) }.toString()
}

fun getRegions(input: List<List<Char>>): MutableSet<Region> {
    val regions = mutableSetOf<Region>()
    for (r in input.indices) {
        for (c in input[r].indices) {
            val type = input[r][c]
            val pos = Position(r, c)
            val touchingMatchingRegions = getTouchingMatchingRegions(type, pos, regions)
            if (touchingMatchingRegions.isEmpty()) {
                regions += Region(type, mutableSetOf(pos))
            } else if (touchingMatchingRegions.size == 1) {
                touchingMatchingRegions.first().plots += pos
            } else {
                touchingMatchingRegions.forEach { regions.remove(it) }
                val merged = mergeRegions(touchingMatchingRegions)
                merged.plots += pos
                regions += merged
            }
        }
    }
    return regions
}

fun getTouchingMatchingRegions(type: Char, pos: Position, regions: Set<Region>): Set<Region> {
    return regions.filter { it.type == type && it.isTouching(pos) }.toSet()
}

fun mergeRegions(regions: Set<Region>): Region {
    val regionList = regions.toList()
    val combined = regionList[0]
    for (i in 1..<regionList.size) {
        combined.plots.addAll(regionList[i].plots)
    }
    return combined
}

fun numBorderEdges(pos: Position, input: List<List<Char>>): Long {
    val type = input[pos.x][pos.y]
    val offsets = listOf(Position(1, 0), Position(-1, 0), Position(0, 1), Position(0, -1))
    var count = 0
    for (offset in offsets) {
        val offsetPos = pos + offset
        if (!offsetMatchesType(offsetPos, input, type)) {
            count++
        }
    }
    return count.toLong()
}

fun numTouchingBorderEdges(pos: Position, input: List<List<Char>>): Long {
    val type = input[pos.x][pos.y]
    val cornerOffsets = listOf(
        CornerOffset(Position(1, 0), Position(0, 1), Position(1, 1)), // Down right
        CornerOffset(Position(1, 0), Position(0, -1), Position(1, -1)), // Down left
        CornerOffset(Position(-1, 0), Position(0, 1), Position(-1, 1)), // Up right
        CornerOffset(Position(-1, 0), Position(0, -1), Position(-1, -1)) // Up left
    )
    var count = 0
    for (cornerOffset in cornerOffsets) {
        val corner = CornerOffset(pos + cornerOffset.adj1, pos + cornerOffset.adj2, pos + cornerOffset.diag)
        if (isOutsideCorner(corner, input, type) || isInsideCorner(corner, input, type)) {
            count++
        }
    }
    return count.toLong()
}

fun isOutsideCorner(
    corner: CornerOffset,
    input: List<List<Char>>,
    type: Char
) = !offsetMatchesType(corner.adj1, input, type) && !offsetMatchesType(corner.adj2, input, type)

fun isInsideCorner(
    corner: CornerOffset,
    input: List<List<Char>>,
    type: Char
) = offsetMatchesType(corner.adj1, input, type) &&
        offsetMatchesType(corner.adj2, input, type) &&
        !offsetMatchesType(corner.diag, input, type)

fun offsetMatchesType(offsetPos: Position, input: List<List<Char>>, type: Char): Boolean {
    return inBounds(offsetPos, input) && input[offsetPos.x][offsetPos.y] == type
}

fun inBounds(pos: Position, input: List<List<Char>>): Boolean {
    return (pos.x >= 0 && pos.x < input.size && pos.y >= 0 && pos.y < input[0].size)
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

    override fun toString(): String {
        return "($x, $y)"
    }
}

class Region(val type: Char, val plots: MutableSet<Position>) {
    fun isTouching(pos: Position): Boolean {
        val offsets = listOf(Position(1, 0), Position(-1, 0), Position(0, 1), Position(0, -1))
        for (offset in offsets) {
            if ((pos + offset) in plots) {
                return true
            }
        }
        return false
    }

    fun area(): Long {
        return plots.size.toLong()
    }

    fun perimeter(input: List<List<Char>>): Long {
        return plots.sumOf { numBorderEdges(it, input) }
    }

    fun numCorners(input: List<List<Char>>): Long {
        return plots.sumOf { numTouchingBorderEdges(it, input) }
    }

    operator fun plus(other: Region): Region {
        return Region(type, (plots + other.plots).toMutableSet())
    }
}

class CornerOffset(val adj1: Position, val adj2: Position, val diag: Position)