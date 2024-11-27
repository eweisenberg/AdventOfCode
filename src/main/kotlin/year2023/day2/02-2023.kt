package year2023.day2

import utils.InputParser
import kotlin.math.max

fun main() {
    val input = InputParser.linesToStrings("src/main/resources/input2023/input02.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<String>): String {
    var sum = 0
    outer@ for (game in input) {
        val cubes = getCubes(game)
        for (cube in cubes) {
            val (color, num) = colorNumPair(cube)
            if (color == "red" && num > 12 || color == "green" && num > 13 || color == "blue" && num > 14) {
                continue@outer
            }
        }
        sum += getId(game)
    }
    return sum.toString()
}

fun part2(input: List<String>): String {
    var sum = 0
    for (game in input) {
        var maxRed = Int.MIN_VALUE
        var maxGreen = Int.MIN_VALUE
        var maxBlue = Int.MIN_VALUE
        val cubes = getCubes(game)
        for (cube in cubes) {
            val (color, num) = colorNumPair(cube)
            when (color) {
                "red" -> maxRed = max(maxRed, num)
                "green" -> maxGreen = max(maxGreen, num)
                "blue" -> maxBlue = max(maxBlue, num)
            }
        }
        sum += maxRed * maxGreen * maxBlue
    }
    return sum.toString()
}

fun getCubes(game: String): List<String> {
    return game.substringAfter(": ").split(", ", "; ")
}

fun getId(game: String): Int {
    return game.substring(game.indexOf(' ') + 1, game.indexOf(':')).toInt()
}

private fun colorNumPair(cube: String): Pair<String, Int> {
    val color = cube.substring(cube.indexOf(' ') + 1)
    val num = cube.substring(0, cube.indexOf(' ')).toInt()
    return Pair(color, num)
}