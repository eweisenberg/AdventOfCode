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

    fun linesToStringPairs(filename: String, delimiter: String): List<Pair<String, String>> {
        return File(filename).readLines().map {
            val strings: List<String> = it.split(delimiter)
            Pair(strings[0], strings[1])
        }
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

    fun oneIntListDigits(filename: String): List<Int> {
        return oneCharList(filename).map { it - '0' }
    }

    fun oneLongList(filename: String, delimiter: String): List<Long> {
        return File(filename).readText().split(delimiter).map(String::toLong)
    }
}