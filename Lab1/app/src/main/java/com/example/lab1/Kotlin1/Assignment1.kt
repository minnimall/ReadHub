package com.example.lab1.Kotlin1

fun main() {
    val studentScore = arrayOf(48, 65, 71, 81, 56)
    println("There are ${studentScore.size} subjects in the array.")

    calculateGPAX(studentScore)
}

fun calculateGPAX(scoreArr: Array<Int>) {
    var i: Int = 1
    var totalCredit: Int = 0
    var totalGradePoints: Double = 0.0
    val creditPerSubject = 3

    for (value in scoreArr) {
        val grade: String = when (value) {
            in 0..50 -> "F"
            in 50..54 -> "D"
            in 55..59 -> "D+"
            in 60..64 -> "C"
            in 65..69 -> "C+"
            in 70..74 -> "B"
            in 75..79 -> "B+"
            in 80..100 -> "A"
            else -> "error"
        }

        val gradePoint: Double = when (grade) {
            "A" -> 4.0
            "B+" -> 3.5
            "B" -> 3.0
            "C+" -> 2.5
            "C" -> 2.0
            "D+" -> 1.5
            "D" -> 1.0
            "F" -> 0.0
            else -> 0.0
        }

        totalCredit += creditPerSubject
        totalGradePoints += gradePoint * creditPerSubject

        println("Grade of Subject Number $i: $value = $grade : $gradePoint")
        i++
    }

    val gpax = totalGradePoints / totalCredit
    val GP = scoreArr.joinToString(" + ", "(", ")") { "(${gradeToPoint(it)}*$creditPerSubject)" }
    println("GPAX = $GP / $totalCredit = $gpax")
}

fun gradeToPoint(score: Int): Double {
    return when (score) {
        in 0..50 -> 0.0
        in 50..54 -> 1.0
        in 55..59 -> 1.5
        in 60..64 -> 2.0
        in 65..69 -> 2.5
        in 70..74 -> 3.0
        in 75..79 -> 3.5
        in 80..100 -> 4.0
        else -> 0.0
    }
}