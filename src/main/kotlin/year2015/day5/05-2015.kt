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
    val badStrings = listOf("ab", "cd", "pq", "xy")
    return input.count { countVowels(it) >= 3 && containsDoubleLetter(it) && notContainsStrings(it, badStrings) }
        .toString()
}

fun part2(input: List<String>): String {
    return input.count { containsTwoPairs(it) && containsRepeatWithOneLetterBetween(it) }.toString()
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
    for (i in 0..(s.length - 2)) {
        val pair = s.substring(i, i + 2)
        for (j in (i + 2)..(s.length - 2)) {
            if (s.substring(j, j + 2) == pair) {
                return true
            }
        }
    }
    return false
}

fun containsRepeatWithOneLetterBetween(s: String): Boolean {
    for (i in 1..(s.length - 2)) {
        if (s[i - 1] == s[i + 1]) {
            return true
        }
    }
    return false
}