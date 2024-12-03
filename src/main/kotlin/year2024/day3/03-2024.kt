package year2024.day3

import utils.InputParser

fun main() {
    val input = InputParser.oneString("src/main/resources/input2024/input03.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: String): String {
    var total = 0
    val mulInstructions = Regex("mul\\(\\d{1,3},\\d{1,3}\\)").findAll(input)
    for (mulInstruction in mulInstructions) {
        val string = mulInstruction.value
        val num1 = string.substringAfter('(').substringBefore(',').toInt()
        val num2 = string.substringAfter(',').substringBefore(')').toInt()
        total += num1 * num2
    }
    return total.toString()
}

fun part2(input: String): String {
    var enabledInput = ""
    var enabled = true
    var lastIndex = 0
    while (true) {
        val nextIndex = when(enabled) {
            true -> input.indexOf("don't()", lastIndex)
            false -> input.indexOf("do()", lastIndex)
        }
        if (nextIndex == -1) {
            if (enabled) {
                enabledInput += input.substring(lastIndex)
            }
            break
        }
        if (enabled) {
            enabledInput += input.substring(lastIndex, nextIndex)
        }
        enabled = !enabled
        lastIndex = nextIndex
    }

    return part1(enabledInput)
}