package year2024.day9

import utils.InputParser

fun main() {
    val input = InputParser.oneIntList("src/main/resources/input2024/input09.txt")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}

fun part1(input: List<Int>): String {
    val diskMap = input.toMutableList()
    diskMap.add(0)
    val files = mutableListOf<File>()
    val counted = mutableMapOf<File, Int>()
    for (i in 0..<diskMap.size step 2) {
        val newFile = File(i / 2, diskMap[i], diskMap[i + 1])
        files += newFile
        counted[newFile] = 0
    }

    var leftIndex = 0
    var rightIndex = files.size - 1
    var position = 0
    var checksum = 0L
    while (leftIndex < rightIndex) {
        val leftFile = files[leftIndex]
        var rightFile = files[rightIndex]

        // Add left file's file block
        for (i in 1..leftFile.fileLen) {
            checksum += position * leftFile.id
            counted[leftFile] = counted[leftFile]!! + 1
            position++
        }
        leftIndex++

        // Add next number from right file to free blocks
        for (i in 1..leftFile.freeLen) {
            val count = counted[rightFile]!!
            if (count < rightFile.fileLen) {
                checksum += position * rightFile.id
                counted[rightFile] = counted[rightFile]!! + 1
                position++
            } else {
                rightIndex--
                rightFile = files[rightIndex]
                checksum += position * rightFile.id
                counted[rightFile] = counted[rightFile]!! + 1
                position++
            }
        }
    }

    // Add remaining positions from right file
    val rightFile = files[rightIndex]
    while (counted[rightFile]!! < rightFile.fileLen) {
        checksum += position * rightFile.id
        counted[rightFile] = counted[rightFile]!! + 1
        position++
    }
    return checksum.toString()
}

fun part2(input: List<Int>): String {
    val diskMap = input.toMutableList()
    diskMap.add(0)
    val files = mutableListOf<File>()
    val startingPositions = mutableMapOf<File, Int>()
    var nextStartingPosition = 0
    for (i in 0..<diskMap.size step 2) {
        val newFile = File(i / 2, diskMap[i], diskMap[i + 1])
        files += newFile
        startingPositions[newFile] = nextStartingPosition
        nextStartingPosition += newFile.fileLen + newFile.freeLen
    }

    // Move files
    var movingFileIndex = files.size - 1
    var movingFile: File
    var movingFileLen: Int
    outerLoop@ while (true) {
        // Break once all files have been attempted to move left
        if (movingFileIndex < 0) {
            break
        }
        var i = 0
        var beforeFile: File
        var afterFile: File
        movingFile = files[movingFileIndex]
        movingFileLen = movingFile.fileLen
        while (true) {
            // If this file does not fit in any spaces to the left then try the next file
            if (i >= movingFileIndex) {
                movingFileIndex--
                continue@outerLoop
            }
            beforeFile = files[i]
            afterFile = files[i + 1]
            val freeLen = startingPositions[afterFile]!! - (startingPositions[beforeFile]!! + beforeFile.fileLen)
            // If there is enough free space here then break and swap movingFile into this space
            if (freeLen >= movingFileLen) {
                break
            }
            i++
        }

        // Move the correct movingFile and update its starting position
        files.remove(movingFile)
        files.add(i + 1, movingFile)
        startingPositions[movingFile] = startingPositions[beforeFile]!! + beforeFile.fileLen
    }

    // Calculate checksum
    var checksum = 0L
    for (file in files) {
        val startingPosition = startingPositions[file]!!
        for (offset in 0..<file.fileLen) {
            checksum += (startingPosition + offset) * file.id
        }
    }

    return checksum.toString()
}

class File(val id: Int, val fileLen: Int, val freeLen: Int)