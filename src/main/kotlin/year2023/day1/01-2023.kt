package year2023.day1

import utils.InputParser

fun main() {
    val input = InputParser.parseLinesToStrings("src/main/resources/input2023/input01.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<String>): String {
    val nums = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
    return input.sumOf { it.findAnyOf(nums)!!.second.toInt() * 10 + it.findLastAnyOf(nums)!!.second.toInt() }.toString()
}

fun part2(input: List<String>): String {
    val nums = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    return input.sumOf { wordToInt(it.findAnyOf(nums)!!.second) * 10 + wordToInt(it.findLastAnyOf(nums)!!.second) }.toString()
}

fun wordToInt(s: String): Int {
    return when (s) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> s.toInt()
    }
}