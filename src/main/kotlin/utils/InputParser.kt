package utils

import java.io.File

object InputParser {
    fun parseLinesToStrings(filename: String): List<String> {
        return File(filename).readLines()
    }
}