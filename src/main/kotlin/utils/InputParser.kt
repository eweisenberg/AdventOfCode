package utils

import java.io.File

object InputParser {
    fun linesToStrings(filename: String): List<String> {
        return File(filename).readLines()
    }
}