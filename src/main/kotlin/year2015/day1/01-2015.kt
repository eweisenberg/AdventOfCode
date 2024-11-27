package year2015.day1

import utils.InputParser

fun main() {
    val input = InputParser.oneString("src/main/resources/input2015/input01.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: String): String {
    return (input.count { it == '(' } - input.count { it == ')' }).toString()
}

fun part2(input: String): String {
    var floor = 0
    for (i in input.indices) {
        when (input[i]) {
            '(' -> floor++
            ')' -> floor--
        }
        if (floor < 0) {
            return (i + 1).toString()
        }
    }
    return "0"
}