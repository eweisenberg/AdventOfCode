package year2023.day3

import utils.InputParser

fun main() {
    val input = InputParser.linesToChar2D("src/main/resources/input2023/input03.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<List<Char>>): String {
    val symbolPoints = mutableSetOf<Pair<Int, Int>>()
    val partNumbers = mutableListOf<PartNumber>()
    for ((rowIndex, row) in input.withIndex()) {
        var colIndex = 0
        while (colIndex < row.size) {
            val char = input[rowIndex][colIndex]
            if (isSymbol(char)) {
                symbolPoints.add(Pair(rowIndex, colIndex))
            } else if (char.isDigit()) {
                val startPoint = Pair(rowIndex, colIndex)
                var endCol = colIndex
                while (endCol < row.size && row[endCol].isDigit()) {
                    endCol++
                }
                val endPoint = Pair(rowIndex, endCol - 1)
                partNumbers.add(PartNumber(startPoint, endPoint))
                colIndex = endPoint.second
            }
            colIndex++
        }
    }

    var sum = 0
    for (partNumber in partNumbers) {
        if (isTouchingSymbol(input, partNumber.startPoint) || isTouchingSymbol(input, partNumber.endPoint)) {
            sum += partNumber.getNumber(input)
        }
    }

    return sum.toString()
}

fun part2(input: List<List<Char>>): String {
    val asteriskPoints = mutableSetOf<Pair<Int, Int>>()
    val partNumbers = mutableListOf<PartNumber>()
    for ((rowIndex, row) in input.withIndex()) {
        var colIndex = 0
        while (colIndex < row.size) {
            val char = input[rowIndex][colIndex]
            if (char == '*') {
                asteriskPoints.add(Pair(rowIndex, colIndex))
            } else if (char.isDigit()) {
                val startPoint = Pair(rowIndex, colIndex)
                var endCol = colIndex
                while (endCol < row.size && row[endCol].isDigit()) {
                    endCol++
                }
                val endPoint = Pair(rowIndex, endCol - 1)
                partNumbers.add(PartNumber(startPoint, endPoint))
                colIndex = endPoint.second
            }
            colIndex++
        }
    }

    return asteriskPoints
        .map { touchingPartNumbers(partNumbers, it) }
        .filter { it.size == 2 }
        .sumOf {
            val (a, b) = it.toList()
            a.getNumber(input) * b.getNumber(input)
        }
        .toString()
}

fun isSymbol(char: Char): Boolean {
    return !"0123456789.".contains(char)
}

fun isTouchingSymbol(schematic: List<List<Char>>, point: Pair<Int, Int>): Boolean {
    for (row in (point.first - 1)..(point.first + 1)) {
        for (col in (point.second - 1)..(point.second + 1)) {
            if (isSymbol(schematic.getOrElse(row, { emptyList() }).getOrElse(col, { '.' }))) {
                return true
            }
        }
    }
    return false
}

fun touchingPartNumbers(partNumbers: List<PartNumber>, point: Pair<Int, Int>): Set<PartNumber> {
    val touchingPartNumbers = mutableSetOf<PartNumber>()
    for (row in (point.first - 1)..(point.first + 1)) {
        for (col in (point.second - 1)..(point.second + 1)) {
            val curPoint = Pair(row, col)
            for (partNumber in partNumbers) {
                if (partNumber.startPoint == curPoint || partNumber.endPoint == curPoint) {
                    touchingPartNumbers.add(partNumber)
                }
            }
        }
    }
    return touchingPartNumbers
}

class PartNumber(val startPoint: Pair<Int, Int>, val endPoint: Pair<Int, Int>) {

    fun getNumber(schematic: List<List<Char>>): Int {
        val row = startPoint.first
        var col = startPoint.second
        var number = schematic[row][col] - '0'
        while (col != endPoint.second) {
            col++
            number *= 10
            number += schematic[row][col] - '0'
        }
        return number
    }
}