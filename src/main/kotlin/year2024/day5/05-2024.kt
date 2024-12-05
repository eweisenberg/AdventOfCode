package year2024.day5

import utils.InputParser

fun main() {
    val input = InputParser.linesToStrings("src/main/resources/input2024/input05.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<String>): String {
    val orderingRules = mutableMapOf<Int, MutableSet<Int>>()
    val pagesList = mutableListOf<List<Int>>()
    getOrderingRulesAndPagesList(input, orderingRules, pagesList)

    var total = 0
    outerLoop@ for (pages in pagesList) {
        for (pageIndex in pages.indices) {
            val curPage = pages[pageIndex]
            for (i in 0..<pageIndex) {
                if (pages[i] in orderingRules.getOrDefault(curPage, setOf())) {
                    continue@outerLoop
                }
            }
        }
        total += pages[pages.size / 2]
    }

    return total.toString()
}

fun part2(input: List<String>): String {
    val orderingRules = mutableMapOf<Int, MutableSet<Int>>()
    val pagesList = mutableListOf<List<Int>>()
    getOrderingRulesAndPagesList(input, orderingRules, pagesList)

    val incorrectPagesList = mutableListOf<List<Int>>()
    outerLoop@ for (pages in pagesList) {
        for (pageIndex in pages.indices) {
            val curPage = pages[pageIndex]
            for (i in 0..<pageIndex) {
                if (pages[i] in orderingRules.getOrDefault(curPage, setOf())) {
                    incorrectPagesList.add(pages)
                    continue@outerLoop
                }
            }
        }
    }

    val sortedIncorrectPagesList = mutableListOf<List<Int>>()
    for (pages in incorrectPagesList) {
        sortedIncorrectPagesList += pages.sortedWith { a, b ->
            if (b in orderingRules.getOrDefault(a, setOf())) -1 else 1
        }
    }

    var total = 0
    for (pages in sortedIncorrectPagesList) {
        total += pages[pages.size / 2]
    }

    return total.toString()
}

private fun getOrderingRulesAndPagesList(
    input: List<String>,
    orderingRules: MutableMap<Int, MutableSet<Int>>,
    pagesList: MutableList<List<Int>>
) {
    var pastSeparator = false
    for (line in input) {
        if (line.isEmpty()) {
            pastSeparator = true
            continue
        }
        if (!pastSeparator) {
            val before = line.substringBefore("|").toInt()
            val after = line.substringAfter('|').toInt()
            if (orderingRules[before] == null) {
                orderingRules[before] = mutableSetOf(after)
            } else {
                orderingRules[before]!!.add(after)
            }
        } else {
            pagesList.add(line.split(',').map { it.toInt() })
        }
    }
}