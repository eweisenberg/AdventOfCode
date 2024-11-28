package utils

import java.io.File

object InputParser {
    fun linesToStrings(filename: String): List<String> {
        return File(filename).readLines()
    }

    fun linesToChar2D(filename: String): List<List<Char>> {
        return File(filename).readLines().map { it.toCharArray().toList() }
    }

    fun linesToIntList(filename: String, delimeter: String): List<List<Int>> {
        return File(filename).readLines().map { it.split(delimeter).map(String::toInt) }
    }

    fun oneString(filename: String): String {
        return File(filename).readText()
    }

    fun oneCharList(filename: String): List<Char> {
        return File(filename).readText().toCharArray().toList()
    }
}