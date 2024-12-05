package year2024.day4

import utils.InputParser

fun main() {
    val input = InputParser.linesToChar2D("src/main/resources/input2024/input04.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<List<Char>>): String {
    val correctStrings = listOf("XMAS", "SAMX")
    var count = 0

    count += horizontalOccurrences(input, correctStrings)
    count += verticalOccurrences(input, correctStrings)
    count += diagonalOccurrencesDownRight(input, correctStrings)
    count += diagonalOccurrencesUpRight(input, correctStrings)

    return count.toString()
}

fun part2(input: List<List<Char>>): String {
    return xOccurrences(input, listOf("MAS", "SAM")).toString()
}

private fun horizontalOccurrences(input: List<List<Char>>, correctStrings: List<String>): Int {
    var count = 0
    for (r in input.indices) {
        for (c in 0..input[0].size - 4) {
            val str = String(charArrayOf(input[r][c], input[r][c + 1], input[r][c + 2], input[r][c + 3]))
            if (str in correctStrings) {
                count++
            }
        }
    }
    return count
}

private fun verticalOccurrences(input: List<List<Char>>, correctStrings: List<String>): Int {
    var count = 0
    for (c in input[0].indices) {
        for (r in 0..input.size - 4) {
            val str = String(charArrayOf(input[r][c], input[r + 1][c], input[r + 2][c], input[r + 3][c]))
            if (str in correctStrings) {
                count++
            }
        }
    }
    return count
}

private fun diagonalOccurrencesDownRight(input: List<List<Char>>, correctStrings: List<String>): Int {
    var count = 0
    for (r in 0..input.size - 4) {
        for (c in 0..input[0].size - 4) {
            val str = String(charArrayOf(input[r][c], input[r + 1][c + 1], input[r + 2][c + 2], input[r + 3][c + 3]))
            if (str in correctStrings) {
                count++
            }
        }
    }
    return count
}

private fun diagonalOccurrencesUpRight(input: List<List<Char>>, correctStrings: List<String>): Int {
    var count = 0
    for (r in 3..<input.size) {
        for (c in 0..input[0].size - 4) {
            val str = String(charArrayOf(input[r][c], input[r - 1][c + 1], input[r - 2][c + 2], input[r - 3][c + 3]))
            if (str in correctStrings) {
                count++
            }
        }
    }
    return count
}

private fun xOccurrences(input: List<List<Char>>, correctStrings: List<String>): Int {
    var count = 0
    for (r in 0..input.size - 3) {
        for (c in 0..input[0].size - 3) {
            val str1 = String(charArrayOf(input[r][c], input[r + 1][c + 1], input[r + 2][c + 2]))
            val str2 = String(charArrayOf(input[r + 2][c], input[r + 1][c + 1], input[r][c + 2]))
            if (str1 in correctStrings && str2 in correctStrings) {
                count++
            }
        }
    }
    return count
}