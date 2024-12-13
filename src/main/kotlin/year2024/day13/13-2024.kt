package year2024.day13

import utils.InputParser

fun main() {
    val input = InputParser.splitStrings2D("src/main/resources/input2024/input13.txt", "\n\n", "\n")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<List<String>>): String {
    val clawMachines = input.map {
        ClawMachine(
            it[0].substringAfter("X+").substringBefore(",").toLong(),
            it[0].substringAfter("Y+").toLong(),
            it[1].substringAfter("X+").substringBefore(",").toLong(),
            it[1].substringAfter("Y+").toLong(),
            it[2].substringAfter("X=").substringBefore(",").toLong(),
            it[2].substringAfter("Y=").toLong(),
        )
    }

    return clawMachines.map { getButtonPresses(it) }
        .filter { it.isIntegerPresses() }
        .sumOf { it.a * 3 + it.b }
        .toLong().toString()
}

fun part2(input: List<List<String>>): String {
    val clawMachines = input.map {
        ClawMachine(
            it[0].substringAfter("X+").substringBefore(",").toLong(),
            it[0].substringAfter("Y+").toLong(),
            it[1].substringAfter("X+").substringBefore(",").toLong(),
            it[1].substringAfter("Y+").toLong(),
            it[2].substringAfter("X=").substringBefore(",").toLong() + 10000000000000,
            it[2].substringAfter("Y=").toLong() + 10000000000000,
        )
    }

    return clawMachines.map { getButtonPresses(it) }
        .filter { it.isIntegerPresses() }
        .sumOf { it.a * 3 + it.b }
        .toLong().toString()
}

fun getButtonPresses(clawMachine: ClawMachine): ButtonPresses {
    // Cramer's rule
    val det = determinant(clawMachine.ax, clawMachine.bx, clawMachine.ay, clawMachine.by)
    val detX = determinant(clawMachine.px, clawMachine.bx, clawMachine.py, clawMachine.by)
    val detY = determinant(clawMachine.ax, clawMachine.px, clawMachine.ay, clawMachine.py)
    return ButtonPresses(detX.toDouble() / det, detY.toDouble() / det)
}

fun determinant(a: Long, b: Long, c: Long, d: Long): Long {
    return a * d - b * c
}

class ClawMachine(val ax: Long, val ay: Long, val bx: Long, val by: Long, val px: Long, val py: Long) {
    override fun toString(): String {
        return "A: ($ax, $ay), B: ($bx, $by), Prize: ($px, $py)"
    }
}

class ButtonPresses(val a: Double, val b: Double) {
    fun isIntegerPresses() = a.toLong().toDouble() == a && b.toLong().toDouble() == b

    override fun toString(): String {
        return "A: $a, B: $b"
    }
}