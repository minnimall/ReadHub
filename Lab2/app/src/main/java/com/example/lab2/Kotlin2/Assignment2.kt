package com.example.lab2.Kotlin2

data class Subject(val id: String, val name: String, val credit: Int)

//class แม่ของ teacher
open class Person (val fName: String, val lName: String, val deptName: String){

    val firstName: String = fName.replaceFirstChar { it.uppercase() }
    val lastName: String = lName.replaceFirstChar { it.uppercase() }
    val department = "$deptName, College of Computing"

    open fun showDetail(){
        println("$firstName is at $department.")
    }

    companion object { // companion = สามารถเรียกใช้ผ่าน ตัว class และตามด้วยชื่อ function ได้เลย
        fun showCompanion(first_Name:String, last_Name:String ,age:Int){
            println("Person is called from companion object : $first_Name $last_Name is $age years old.")
        }
    }
}


class Student(fName: String, lName: String, deptName: String) : Person(fName, lName, deptName) {
    private var creditTotal: Int = 0
    private var gradeTotal: Double = 0.0

    override fun showDetail() {
        println("$firstName is a student at $department.")
    }

    fun gradeEnroll(subj: Subject, score: Int) {
        fun calculateGrade(score: Int): String {
            return when {
                score < 50 -> "F"
                score < 55 -> "D"
                score < 60 -> "D+"
                score < 65 -> "C"
                score < 70 -> "C+"
                score < 75 -> "B"
                score < 80 -> "B+"
                else -> "A"
            }
        }

        fun calculateGradeValue(score: Int): Double {
            return when (calculateGrade(score)) {
                "A" -> 4.0
                "B+" -> 3.5
                "B" -> 3.0
                "C+" -> 2.5
                "C" -> 2.0
                "D+" -> 1.5
                "D" -> 1.0
                else -> 0.0
            }
        }

        println(subj.toString() + " Score: $score, Grade: ${calculateGrade(score)}")
        gradeTotal += subj.credit * calculateGradeValue(score)
        creditTotal += subj.credit
    }

    fun displayGpa() {
        val total = gradeTotal / creditTotal
        val gpa = String.format("%.2f", total)
        println("$firstName's GPA is $gpa")
    }
}

fun main() {
    val subject1 = Subject("SC362007", "Mobile Device Programming", 3)
    var subject2 = Subject("SC362005", "Database Analysis and Design", 3)
    var subject3 = Subject("SC361003", "Object Oriented Concepts and Programming", 1)

    var person3 = Student("Grace", "Moore", "Information Technology",)
    println("Member NO 5 : " + person3.firstName + " " + person3.lastName)
    person3.showDetail()
    person3.gradeEnroll(subject1, 65)
    person3.gradeEnroll(subject2, 73)
    person3.gradeEnroll(subject3, 98)
    person3.displayGpa()
    println()
}