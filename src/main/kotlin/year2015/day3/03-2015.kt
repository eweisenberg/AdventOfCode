package year2015.day3

import utils.InputParser

fun main() {
    val input = InputParser.oneCharList("src/main/resources/input2015/input03.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<Char>): String {
    val visitedHouses = mutableSetOf<Pair<Int, Int>>(Pair(0, 0))
    var x = 0
    var y = 0
    for (c in input) {
        when (c) {
            '>' -> x++
            '<' -> x--
            '^' -> y++
            'v' -> y--
        }
        visitedHouses.add(Pair(x, y))
    }
    return visitedHouses.size.toString()
}

fun part2(input: List<Char>): String {
    val santaInput = input.filterIndexed { index, _ -> index % 2 == 0 }
    val roboInput = input.filterIndexed { index, _ -> index % 2 == 1 }
    val visitedHouses = mutableSetOf<Pair<Int, Int>>(Pair(0, 0))
    var x = 0
    var y = 0
    for (c in santaInput) {
        when (c) {
            '>' -> x++
            '<' -> x--
            '^' -> y++
            'v' -> y--
        }
        visitedHouses.add(Pair(x, y))
    }
    x = 0
    y = 0
    for (c in roboInput) {
        when (c) {
            '>' -> x++
            '<' -> x--
            '^' -> y++
            'v' -> y--
        }
        visitedHouses.add(Pair(x, y))
    }
    return visitedHouses.size.toString()
}