import java.io.File
import kotlin.math.absoluteValue

fun main() {
    Day01().printSolutions()
}

class Day01 {
    var lines: List<String> = File("inputs/inputDay01").readLines()
    val dial = Dial(position = 50, size = 100)
    val instructions = lines.map { Dial.Instruction.from(it) }

    fun printSolutions() {
        for (instruction in instructions) {
            dial.perform(instruction)
        }

        println("Puzzle answer to part 1 is ${puzzle1Solution()}")
        println("Puzzle answer to part 2 is ${puzzle2Solution()}")
    }

    private fun puzzle1Solution(): String {
        return dial.timesEndedOnZero.toString()
    }

    private fun puzzle2Solution(): String {
        return dial.timesPassedZero.toString()
    }
}

class Dial(var position: Int, val size: Int) {
    var timesEndedOnZero = 0
    var timesPassedZero = 0

    fun perform(instruction: Instruction) {
        move(instruction.signedDistance)

        if (position == 0) {
            timesEndedOnZero++
        }
    }

    private fun move(distance: Int) {
        val stepWidth = if (distance >= 0) { 1 } else { -1 }

        repeat(distance.absoluteValue) {
            position += stepWidth
            position = position.mod(size)
            println(position)

            if (position == 0) {
                timesPassedZero++
            }
        }
    }

    data class Instruction(val direction: Direction, val distance: Int) {
        val signedDistance: Int
            get() {
                return when (direction) {
                    Direction.LEFT -> -distance
                    Direction.RIGHT -> distance
                }
            }

        enum class Direction {
            LEFT, RIGHT;

            companion object {
                fun from(string: String): Direction {
                    return if (string == "L") {
                        LEFT
                    } else {
                        RIGHT
                    }
                }
            }
        }

        companion object {
            fun from(string: String): Instruction {
                val directionString =  string.take(1)
                val direction = Direction.from(directionString)

                val distanceString = string.substring(startIndex = 1)
                val distance = distanceString.toInt()

                return Instruction(direction, distance)
            }
        }
    }
}
