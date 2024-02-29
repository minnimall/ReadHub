package com.example.lab1.Kotlin1

fun main() {

    val studentScore = arrayOf(775, 362, 593, 616, 930, 900)
    println("There are ${studentScore.size} students in array.")

    calculateLevel(studentScore)

}

fun calculateLevel(scoreArr: Array<Int>) {

    var i: Int = 1
    var level: String

    for (value in scoreArr) {

        level = when (value) {

            in 0..10 -> "Wrong Value"
            in 10..250 -> "Novice"
            in 251..254 -> "Wrong Value"
            in 255..400 -> "Elementary"
            in 401..604 -> "Wrong Value"
            in 605..780 -> "Basic Working"
            in 781..784 -> "Wrong Value"
            in 785..900 -> "Advanced Working Proficiency"
            in 901..904 -> "Wrong Value"
            in 905..990 -> "Professional Proficiency"
            else -> "Wrong Value"
        }

        println("Level of Student Number $i $value = $level")
        i++
    }

}