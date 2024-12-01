package utils

import java.io.File

object InputParser {
    fun linesToStrings(filename: String): List<String> {
        return File(filename).readLines()
    }

    fun linesToChar2D(filename: String): List<List<Char>> {
        return File(filename).readLines().map { it.toCharArray().toList() }
    }

    fun linesToInt2D(filename: String, delimiter: String): List<List<Int>> {
        return File(filename).readLines().map { it.split(delimiter).map(String::toInt) }
    }

    fun linesToIntPairs(filename: String, delimiter: String): List<Pair<Int, Int>> {
        return File(filename).readLines().map {
            val numbers: List<Int> = it.split(delimiter).map(String::toInt)
            Pair(numbers[0], numbers[1])
        }
    }

    fun oneString(filename: String): String {
        return File(filename).readText()
    }

    fun oneCharList(filename: String): List<Char> {
        return File(filename).readText().toCharArray().toList()
    }
}