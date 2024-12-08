package year2024.day8

import utils.InputParser

fun main() {
    val input = InputParser.linesToChar2D("src/main/resources/input2024/input08.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<List<Char>>): String {
    val rows = input.size
    val cols = input[0].size
    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    for (row in 0..<rows) {
        for (column in 0..<cols) {
            val frequency = input[row][column]
            if (frequency == '.') {
                continue
            }
            if (!antennas.containsKey(frequency)) {
                antennas[frequency] = mutableListOf()
            }
            antennas[frequency]!!.add(Pair(row, column))
        }
    }

    val antinodes = mutableSetOf<Pair<Int, Int>>()
    for (locations in antennas.values) {
        val numLocations = locations.size
        for (i in 0..<numLocations) {
            for (j in (i + 1)..<numLocations) {
                val antenna1 = locations[i]
                val antenna2 = locations[j]
                val vectorBetween = pairDifference(antenna2, antenna1)
                antinodes.add(pairDifference(antenna1, vectorBetween))
                antinodes.add(pairSum(antenna2, vectorBetween))
            }
        }
    }
    return antinodes.filter { inBounds(it, rows, cols) }.size.toString()
}

fun part2(input: List<List<Char>>): String {
    val rows = input.size
    val cols = input[0].size
    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    for (row in 0..<rows) {
        for (column in 0..<cols) {
            val frequency = input[row][column]
            if (frequency == '.') {
                continue
            }
            if (!antennas.containsKey(frequency)) {
                antennas[frequency] = mutableListOf()
            }
            antennas[frequency]!!.add(Pair(row, column))
        }
    }

    val antinodes = mutableSetOf<Pair<Int, Int>>()
    for (locations in antennas.values) {
        val numLocations = locations.size
        for (i in 0..<numLocations) {
            for (j in (i + 1)..<numLocations) {
                val antenna1 = locations[i]
                val antenna2 = locations[j]
                val vectorBetween = pairDifference(antenna2, antenna1)
                var antennaForward = antenna2
                while (inBounds(antennaForward, rows, cols)) {
                    antinodes.add(antennaForward)
                    antennaForward = pairSum(antennaForward, vectorBetween)
                }
                var antennaBackward = antenna2
                while (inBounds(antennaBackward, rows, cols)) {
                    antinodes.add(antennaBackward)
                    antennaBackward = pairDifference(antennaBackward, vectorBetween)
                }
            }
        }
    }
    return antinodes.size.toString()
}

fun inBounds(location: Pair<Int, Int>, rows: Int, cols: Int): Boolean {
    return location.first in 0..<rows && location.second in 0..<cols
}

fun pairSum(pair1: Pair<Int, Int>, pair2: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(pair1.first + pair2.first, pair1.second + pair2.second)
}

fun pairDifference(pair1: Pair<Int, Int>, pair2: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(pair1.first - pair2.first, pair1.second - pair2.second)
}