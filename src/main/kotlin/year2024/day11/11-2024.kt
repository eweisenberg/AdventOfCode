package year2024.day11

import utils.InputParser
import kotlin.math.pow

fun main() {
    val input = InputParser.oneLongList("src/main/resources/input2024/input11.txt", " ")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<Long>): String {
    var stonesCount = mutableMapOf<Long, Long>()
    input.forEach { stonesCount[it] = stonesCount.getOrDefault(it, 0) + 1 }
    for (blinks in 1..25) {
        stonesCount = updateAfterBlink(stonesCount)
    }
    return stonesCount.values.sum().toString()

    /*
    Old brute force solution:

    val stones = input.toMutableList()
    for (blinks in 1..25) {
        var i = 0
        while (i < stones.size) {
            val num = stones[i]
            if (num == 0L) {
                stones[i] = 1L
            } else if (numDigits(num) % 2 == 0) {
                stones[i] = leftHalf(num)
                stones.add(i + 1, rightHalf(num))
                i++
            } else {
                stones[i] = stones[i] * 2024
            }
            i++
        }
    }
    return stones.size.toString()
    */
}

fun part2(input: List<Long>): String {
    var stonesCount = mutableMapOf<Long, Long>()
    input.forEach { stonesCount[it] = stonesCount.getOrDefault(it, 0) + 1 }
    for (blinks in 1..75) {
        stonesCount = updateAfterBlink(stonesCount)
    }
    return stonesCount.values.sum().toString()
}

private fun updateAfterBlink(stonesCount: MutableMap<Long, Long>): MutableMap<Long, Long> {
    val newStonesCount = mutableMapOf<Long, Long>()
    for (stone in stonesCount.keys) {
        val times = stonesCount[stone]
        if (stone == 0L) {
            newStonesCount[1] = newStonesCount.getOrDefault(1, 0) + times!!
        } else if (numDigits(stone) % 2 == 0) {
            val leftHalf = leftHalf(stone)
            val rightHalf = rightHalf(stone)
            newStonesCount[leftHalf] = newStonesCount.getOrDefault(leftHalf, 0) + times!!
            newStonesCount[rightHalf] = newStonesCount.getOrDefault(rightHalf, 0) + times
        } else {
            newStonesCount[stone * 2024] = newStonesCount.getOrDefault(stone * 2024, 0) + times!!
        }
    }
    return newStonesCount
}

fun numDigits(num: Long): Int {
    return num.toString().length
}

fun leftHalf(num: Long): Long {
    return num / 10.0.pow(numDigits(num) / 2).toLong()
}

fun rightHalf(num: Long): Long {
    return num % 10.0.pow(numDigits(num) / 2).toLong()
}