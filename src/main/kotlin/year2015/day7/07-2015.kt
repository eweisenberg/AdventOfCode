package year2015.day7

import utils.InputParser

fun main() {
    val input = InputParser.linesToStringPairs("src/main/resources/input2015/input07.txt", " -> ")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<Pair<String, String>>): String {
    val wires = mutableMapOf<String, Signal>()
    for ((signalInput, id) in input) {
        wires[id] = Signal(signalInput)
    }
    val computedSignals = mutableMapOf<String, UShort>()
    return wires["a"]!!.getSignal(wires, "a", computedSignals).toString()
}

fun part2(input: List<Pair<String, String>>): String {
    val wires = mutableMapOf<String, Signal>()
    for ((signalInput, id) in input) {
        wires[id] = Signal(signalInput)
    }
    val computedSignals = mutableMapOf<String, UShort>(Pair("b", 16076u))
    return wires["a"]!!.getSignal(wires, "a", computedSignals).toString()
}



class Signal(private val signalInput: String) {
    fun getSignal(wires: Map<String, Signal>, id: String, computedSignals: MutableMap<String, UShort>): UShort {
        val signal: UShort = computedSignals.getOrElse(id) {
            if (signalInput.contains("NOT")) {
                val id1 = signalInput.substringAfter(' ')
                wires[id1]!!.getSignal(wires, id1, computedSignals).inv()
            } else if (signalInput.contains("AND")) {
                val id2 = signalInput.substringAfterLast(' ')
                try {
                    val id1 = signalInput.substringBefore(' ').toUShort()
                    id1.and(wires[id2]!!.getSignal(wires, id2, computedSignals))
                } catch (e: NumberFormatException) {
                    val id1 = signalInput.substringBefore(' ')
                    wires[id1]!!.getSignal(wires, id1, computedSignals).and(wires[id2]!!.getSignal(wires, id2, computedSignals))
                }
            } else if (signalInput.contains("OR")) {
                val id1 = signalInput.substringBefore(' ')
                val id2 = signalInput.substringAfterLast(' ')
                wires[id1]!!.getSignal(wires, id1, computedSignals).or(wires[id2]!!.getSignal(wires, id2, computedSignals))
            } else if (signalInput.contains("LSHIFT")) {
                val id1 = signalInput.substringBefore(' ')
                val shiftAmount = signalInput.substringAfterLast(' ').toInt()
                wires[id1]!!.getSignal(wires, id1, computedSignals).toUInt().shl(shiftAmount).toUShort()
            } else if (signalInput.contains("RSHIFT")) {
                val id1 = signalInput.substringBefore(' ')
                val shiftAmount = signalInput.substringAfterLast(' ').toInt()
                wires[id1]!!.getSignal(wires, id1, computedSignals).toUInt().shr(shiftAmount).toUShort()
            } else {
                try {
                    signalInput.toUShort()
                } catch (e: NumberFormatException) {
                    wires[signalInput]!!.getSignal(wires, signalInput, computedSignals)
                }
            }
        }
        computedSignals[id] = signal
        return signal
    }
}