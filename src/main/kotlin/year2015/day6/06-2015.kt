package year2015.day6

import utils.InputParser

fun main() {
    val input = InputParser.linesToStrings("src/main/resources/input2015/input06.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<String>): String {
    val lights = Array(1000) { BooleanArray(1000) }
    for (instructionStr in input) {
        val instruction = instructionStr.split(" ", ",")
        if (instructionStr.startsWith("turn on")) {
            turnOn(
                lights,
                Pair(instruction[2].toInt(), instruction[3].toInt()),
                Pair(instruction[5].toInt(), instruction[6].toInt())
            )
        } else if (instructionStr.startsWith("turn off")) {
            turnOff(
                lights,
                Pair(instruction[2].toInt(), instruction[3].toInt()),
                Pair(instruction[5].toInt(), instruction[6].toInt())
            )
        } else if (instructionStr.startsWith("toggle")) {
            toggle(
                lights,
                Pair(instruction[1].toInt(), instruction[2].toInt()),
                Pair(instruction[4].toInt(), instruction[5].toInt())
            )
        }
    }
    return lights.sumOf { row -> row.count { it } }.toString()
}

fun part2(input: List<String>): String {
    val lights = Array(1000) { IntArray(1000) }
    for (instructionStr in input) {
        val instruction = instructionStr.split(" ", ",")
        if (instructionStr.startsWith("turn on")) {
            increaseBrightness(
                lights,
                Pair(instruction[2].toInt(), instruction[3].toInt()),
                Pair(instruction[5].toInt(), instruction[6].toInt()),
                1
            )
        } else if (instructionStr.startsWith("turn off")) {
            increaseBrightness(
                lights,
                Pair(instruction[2].toInt(), instruction[3].toInt()),
                Pair(instruction[5].toInt(), instruction[6].toInt()),
                -1
            )
        } else if (instructionStr.startsWith("toggle")) {
            increaseBrightness(
                lights,
                Pair(instruction[1].toInt(), instruction[2].toInt()),
                Pair(instruction[4].toInt(), instruction[5].toInt()),
                2
            )
        }
    }
    return lights.sumOf { it.sum() }.toString()
}

fun turnOn(lights: Array<BooleanArray>, coordinate1: Pair<Int, Int>, coordinate2: Pair<Int, Int>) {
    for (r in coordinate1.first..coordinate2.first) {
        for (c in coordinate1.second..coordinate2.second) {
            lights[r][c] = true
        }
    }
}

fun turnOff(lights: Array<BooleanArray>, coordinate1: Pair<Int, Int>, coordinate2: Pair<Int, Int>) {
    for (r in coordinate1.first..coordinate2.first) {
        for (c in coordinate1.second..coordinate2.second) {
            lights[r][c] = false
        }
    }
}

fun toggle(lights: Array<BooleanArray>, coordinate1: Pair<Int, Int>, coordinate2: Pair<Int, Int>) {
    for (r in coordinate1.first..coordinate2.first) {
        for (c in coordinate1.second..coordinate2.second) {
            lights[r][c] = !lights[r][c]
        }
    }
}

fun increaseBrightness(lights: Array<IntArray>, coordinate1: Pair<Int, Int>, coordinate2: Pair<Int, Int>, amount: Int) {
    for (r in coordinate1.first..coordinate2.first) {
        for (c in coordinate1.second..coordinate2.second) {
            lights[r][c] += amount
            if (lights[r][c] < 0) {
                lights[r][c] = 0
            }
        }
    }
}