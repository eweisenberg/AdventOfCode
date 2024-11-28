package year2015.day5

import utils.InputParser

fun main() {
    val input = InputParser.linesToStrings("src/main/resources/input2015/input05.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<String>): String {
    val badStrings = listOf<String>("ab", "cd", "pq", "xy")
    return input.count { countVowels(it) >= 3 && containsDoubleLetter(it) && notContainsStrings(it, badStrings) }
        .toString()
}

fun part2(input: List<String>): String {
    return ""
}

fun countVowels(s: String): Int {
    return s.count { it == 'a' || it == 'e' || it == 'i' || it == 'o' || it == 'u' }
}

fun containsDoubleLetter(s: String): Boolean {
    var prevChar = ' '
    for (c in s) {
        if (prevChar == c) {
            return true
        }
        prevChar = c
    }
    return false
}

fun notContainsStrings(s: String, strings: List<String>): Boolean {
    return strings.all { !s.contains(it) }
}

fun containsTwoPairs(s: String): Boolean {
    for (i in s.indices) {

    }
    return false
}