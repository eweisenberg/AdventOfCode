package year2024.day7

import utils.InputParser
import kotlin.math.pow

fun main() {
    val input = InputParser.linesToStringPairs("src/main/resources/input2024/input07.txt", ": ")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<Pair<String, String>>): String {
    val equations = mutableListOf<Equation>()
    for (equationPair in input) {
        equations += Equation(equationPair.first.toLong(), equationPair.second.split(" ").map(String::toLong))
    }

    return equations.filter { it.isPossible() }.sumOf { it.testValue }.toString()
}

fun part2(input: List<Pair<String, String>>): String {
    val equations = mutableListOf<Equation>()
    for (equationPair in input) {
        equations += Equation(equationPair.first.toLong(), equationPair.second.split(" ").map(String::toLong))
    }

    return equations.filter { it.isPossibleWithConcatenation() }.sumOf { it.testValue }.toString()
}

class Equation(val testValue: Long, private val nums: List<Long>) {
    fun isPossible(): Boolean {
        for (mask in 0..<(2.0.pow(nums.size - 1).toInt())) {
            // 0 = add, 1 = multiply
            var result: Long = nums[0]
            var modifiedMask = mask
            for (i in 1..<nums.size) {
                when (modifiedMask % 2) {
                    0 -> result += nums[i]
                    1 -> result *= nums[i]
                }
                modifiedMask /= 2
            }
            if (result == testValue) {
                return true
            }
        }
        return false
    }

    fun isPossibleWithConcatenation(): Boolean {
        for (mask in 0..<(3.0.pow(nums.size - 1).toInt())) {
            // 0 = add, 1 = multiply, 2 = concatenate
            var result: Long = nums[0]
            var modifiedMask = mask
            for (i in 1..<nums.size) {
                when (modifiedMask % 3) {
                    0 -> result += nums[i]
                    1 -> result *= nums[i]
                    2 -> result = ("$result${nums[i]}").toLong()
                }
                modifiedMask /= 3
            }
            if (result == testValue) {
                return true
            }
        }
        return false
    }
}