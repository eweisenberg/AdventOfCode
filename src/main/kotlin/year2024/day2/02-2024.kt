package year2024.day2

import utils.InputParser
import kotlin.math.abs

fun main() {
    val input = InputParser.linesToInt2D("src/main/resources/input2024/input02.txt", " ")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<List<Int>>): String {
    return input.count { (isAllIncreasing(it) || isAllDecreasing(it)) && isAdjacentDifferenceBetween(it, 1, 3) }
        .toString()
}

fun part2(input: List<List<Int>>): String {
    var count = 0
    for (levels in input) {
        if ((isAllIncreasing(levels) || isAllDecreasing(levels)) && isAdjacentDifferenceBetween(levels, 1, 3)) {
            count++
            continue
        }
        for (i in levels.indices) {
            val levelRemoved = levels.toMutableList()
            levelRemoved.removeAt(i)
            if ((isAllIncreasing(levelRemoved) || isAllDecreasing(levelRemoved)) &&
                isAdjacentDifferenceBetween(levelRemoved, 1, 3)
            ) {
                count++
                break
            }
        }
    }
    return count.toString()
}

fun isAllIncreasing(levels: List<Int>): Boolean {
    for (i in 1 until levels.size) {
        if (levels[i] <= levels[i - 1]) {
            return false
        }
    }
    return true
}

fun isAllDecreasing(levels: List<Int>): Boolean {
    for (i in 1 until levels.size) {
        if (levels[i] >= levels[i - 1]) {
            return false
        }
    }
    return true
}

fun isAdjacentDifferenceBetween(levels: List<Int>, minDifference: Int, maxDifference: Int): Boolean {
    for (i in 1 until levels.size) {
        val difference = abs(levels[i] - levels[i - 1])
        if (difference < minDifference || difference > maxDifference) {
            return false
        }
    }
    return true
}