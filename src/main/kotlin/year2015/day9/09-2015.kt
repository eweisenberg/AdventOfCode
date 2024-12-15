package year2015.day9

import utils.InputParser
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = InputParser.linesToStrings("src/main/resources/input2015/input09.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<String>): String {
    val locations = getLocations(input)
    val distances = getDistances(input)
    val permutations = permutations(locations.toList())
    return minDistance(permutations, distances).toString()
}

fun part2(input: List<String>): String {
    val locations = getLocations(input)
    val distances = getDistances(input)
    val permutations = permutations(locations.toList())
    return maxDistance(permutations, distances).toString()
}

private fun minDistance(permutations: List<List<String>>, distances: Map<LocationPair, Int>): Int {
    var minDistance = Int.MAX_VALUE
    for (permutation in permutations) {
        var distance = 0
        for (i in 1..<permutation.size) {
            distance += distances[LocationPair(permutation[i - 1], permutation[i])]!!
        }
        minDistance = min(minDistance, distance)
    }
    return minDistance
}

private fun maxDistance(permutations: List<List<String>>, distances: Map<LocationPair, Int>): Int {
    var maxDistance = 0
    for (permutation in permutations) {
        var distance = 0
        for (i in 1..<permutation.size) {
            distance += distances[LocationPair(permutation[i - 1], permutation[i])]!!
        }
        maxDistance = max(maxDistance, distance)
    }
    return maxDistance
}

fun getDistances(input: List<String>): Map<LocationPair, Int> {
    val distances = mutableMapOf<LocationPair, Int>()
    for (line in input) {
        val location1 = line.substringBefore(" ")
        val location2 = line.substringAfter(" to ").substringBefore(" = ")
        val distance = line.substringAfterLast(" ").toInt()
        distances[LocationPair(location1, location2)] = distance
    }
    return distances
}

fun getLocations(input: List<String>): MutableSet<String> {
    val locations = mutableSetOf<String>()
    for (line in input) {
        val location1 = line.substringBefore(" ")
        val location2 = line.substringAfter(" to ").substringBefore(" = ")
        locations += location1
        locations += location2
    }
    return locations
}

fun permutations(locations: List<String>): List<List<String>> {
    if (locations.isEmpty()) {
        return listOf(emptyList())
    }

    val permutations = mutableListOf<List<String>>()
    for (i in locations.indices) {
        val location = locations[i]
        val rest = locations.subList(0, i) + locations.subList(i + 1, locations.size)
        for (permutation in permutations(rest)) {
            permutations.add(listOf(location) + permutation)
        }
    }
    return permutations
}

class LocationPair(val location1: String, val location2: String) {
    // Order doesn't matter for location1 and location2

    override fun toString() = "($location1, $location2)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocationPair

        return location1 == other.location1 && location2 == other.location2 ||
                location1 == other.location2 && location2 == other.location1
    }

    override fun hashCode(): Int {
        return location1.hashCode() * location2.hashCode()
    }
}