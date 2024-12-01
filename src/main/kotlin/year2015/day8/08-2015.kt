package year2015.day8

import utils.InputParser

fun main() {
    val input = InputParser.linesToStrings("src/main/resources/input2015/input08.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<String>): String {
    return input.sumOf {
        it.length -
                it.substring(1, it.length - 1)
                    .replace("\\\\", "a")
                    .replace("\\\"", "a")
                    .replace(Regex("\\\\x[0123456789abcdef]{2}"), "a")
                    .length
    }.toString()
}

fun part2(input: List<String>): String {
    return input.sumOf {
        s -> s.count { it == '"' } + s.count { it == '\\' } + 2
    }.toString()
}