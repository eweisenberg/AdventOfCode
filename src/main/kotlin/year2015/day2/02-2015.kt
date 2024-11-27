package year2015.day2

import utils.InputParser
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = InputParser.linesToIntList("src/main/resources/input2015/input02.txt", "x")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<List<Int>>): String {
    return input.sumOf {
        val min = min(min(it[0], it[1]), it[2])
        val max = max(max(it[0], it[1]), it[2])
        val mid = it[0] + it[1] + it[2] - min - max
        3 * min * mid + 2 * min * max + 2 * mid * max
    }.toString()
}

fun part2(input: List<List<Int>>): String {
    return input.sumOf {
        val min = min(min(it[0], it[1]), it[2])
        val max = max(max(it[0], it[1]), it[2])
        val mid = it[0] + it[1] + it[2] - min - max
        2 * (min + mid) + min * mid * max
    }.toString()
}