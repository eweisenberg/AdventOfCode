package year2024.day1

import utils.InputParser
import kotlin.math.abs

fun main() {
    val input = InputParser.linesToIntPairs("src/main/resources/input2024/input01.txt", "   ")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<Pair<Int, Int>>): String {
    val leftNums = mutableListOf<Int>()
    val rightNums = mutableListOf<Int>()
    for (pair in input) {
        leftNums.add(pair.first)
        rightNums.add(pair.second)
    }
    leftNums.sort()
    rightNums.sort()
    var sum = 0
    for (i in leftNums.indices) {
        sum += abs(leftNums[i] - rightNums[i])
    }
    return sum.toString()
}

fun part2(input: List<Pair<Int, Int>>): String {
    val rightFreq = mutableMapOf<Int, Int>()
    for ((_, right) in input) {
        rightFreq[right] = rightFreq.getOrDefault(right, 0) + 1
    }
    var sum = 0
    for ((left, _) in input) {
        sum += rightFreq.getOrDefault(left, 0) * left
    }
    return sum.toString()
}