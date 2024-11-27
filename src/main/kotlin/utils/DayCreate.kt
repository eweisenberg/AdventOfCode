package utils

import java.io.File

fun main() {
    print("Input year: ")
    val year = readln().toInt()
    print("Input day: ")
    val day = readln().toInt()

    val yearDir = File("src/main/kotlin/year$year")
    if (!yearDir.exists()) {
        println("year$year Directory does not exist")
        return
    }

    val dayDir = File("src/main/kotlin/year$year/day$day")
    if (dayDir.exists()) {
        println("Day $day already exists")
        return
    }
    dayDir.mkdir()

    val file = File(dayDir, "${String.format("%02d", day)}-$year.kt")

    file.writeText("""
        package year$year.day$day
        
        import utils.InputParser

        fun main() {
            val input = InputParser.parseLinesToStrings("src/main/resources/input$year/input${String.format("%02d", day)}.txt")
            println("Part 1:")
            println(part1(input))
            println("Part 2:")
            println(part2(input))
        }

        fun part1(input: List<String>): String {
            return ""
        }

        fun part2(input: List<String>): String {
            return ""
        }
    """.trimIndent())
}

//fun main() {
//    val input
//    println("Part 1:")
//    println(part1())
//}
//
//fun part1(input: List<String>): String {
//    return ""
//}
//
//fun part2(input: List<String>): String {
//    return ""
//}