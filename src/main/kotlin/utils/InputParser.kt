package utils

import java.io.File

object InputParser {
    fun linesToStrings(filename: String): List<String> {
        return File(filename).readLines()
    }

    fun linesToChar2D(filename: String): List<List<Char>> {
        return File(filename).readLines().map { it.toCharArray().toList() }
    }
}