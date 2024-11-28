package year2015.day4

import utils.InputParser
import java.security.MessageDigest

fun main() {
    val input = InputParser.oneString("src/main/resources/input2015/input04.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: String): String {
    var md5 = "11111"
    var key = 0
    while (!md5.toCharArray().toList().subList(0, 5).all { it == '0' }) {
        key++
        md5 = md5("$input$key")
    }
    return key.toString()
}

fun part2(input: String): String {
    var md5 = "111111"
    var key = 0
    while (!md5.toCharArray().toList().subList(0, 6).all { it == '0' }) {
        key++
        md5 = md5("$input$key")
    }
    return key.toString()
}

@OptIn(ExperimentalStdlibApi::class)
fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    md.update(input.toByteArray())
    return md.digest().toHexString()
}