package year2024.day14

import utils.InputParser

fun main() {
    val input = InputParser.linesToStrings("src/main/resources/input2024/input14.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<String>): String {
    val areaSize = Vector(101, 103)
    val robots = getRobots(input)
    val finalPositions = mutableMapOf<Vector, Int>()
    robots.map { it.getPositionAfterSeconds(100, areaSize) }
        .forEach { finalPositions[it] = finalPositions.getOrDefault(it, 0) + 1 }

    return safetyFactor(finalPositions, areaSize).toString() // 102155040 too low
}

fun part2(input: List<String>): String {
    return ""
}

fun getRobots(input: List<String>) = input.map {
    Robot(
        Vector(
            it.substringAfter("=").substringBefore(",").toInt(),
            it.substringAfter(",").substringBefore(" ").toInt()
        ),
        Vector(
            it.substringAfterLast("=").substringBeforeLast(",").toInt(),
            it.substringAfterLast(",").toInt()
        )
    )
}

fun safetyFactor(positionCounts: Map<Vector, Int>, areaSize: Vector): Int {
    val center = areaSize / 2
    val quadrantCounts = IntArray(4)
    for (pos in positionCounts.keys) {
        if (pos.x < center.x && pos.y < center.y) {
            quadrantCounts[0] += positionCounts[pos]!!
        } else if (pos.x < center.x && pos.y > center.y) {
            quadrantCounts[1] += positionCounts[pos]!!
        } else if (pos.x > center.x && pos.y < center.y) {
            quadrantCounts[2] += positionCounts[pos]!!
        } else if (pos.x > center.x && pos.y > center.y) {
            quadrantCounts[3] += positionCounts[pos]!!
        }
    }
    return quadrantCounts[0] * quadrantCounts[1] * quadrantCounts[2] * quadrantCounts[3]
}

class Vector(val x: Int, val y: Int) {
    operator fun plus(v: Vector) = Vector(x + v.x, y + v.y)

    operator fun times(scalar: Int) = Vector(x * scalar, y * scalar)

    operator fun div(scalar: Int) = Vector(x / scalar, y / scalar)

    operator fun rem(v: Vector) = Vector(x % v.x, y % v.y)

    override fun toString() = "($x, $y)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vector

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

class Robot(val position: Vector, val velocity: Vector) {
    fun getPositionAfterSeconds(seconds: Int, areaSize: Vector) =
        (((position + velocity * seconds) % areaSize) + areaSize) % areaSize

    override fun toString() = "position: $position, velocity: $velocity"
}