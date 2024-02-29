package com.example.lab11sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

    companion object{
        private val DB_NAME = "StudentDB"
        private val DB_VERSION = 1
        private val TABLE_NAME = "student"
        private val COLUMN_ID = "id"
        private val COLUMN_NAME = "name"
        private val COLUMN_GENDER = "gender"
        private val COLUMN_AGE = "age"
        private val sqliteHelper: DatabaseHelper? = null

        @Synchronized  //ทำงานเป็นจังหวะตามลำดับ ให้เสร็จเป็นงานๆไป
        fun getInstance(c: Context): DatabaseHelper{ //ตัวทำงานให้ sqlite
            if(sqliteHelper == null){ //เช็คว่า sqlhelp เป็นค่าว่างไหม
                return DatabaseHelper(c.applicationContext)
            } else {
                return sqliteHelper
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase?) { //สร้าง database
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME($COLUMN_ID TEXT PRIMARY KEY,"+
                "$COLUMN_NAME TEXT," + "$COLUMN_GENDER TEXT," + "$COLUMN_AGE INTEGER)"
        db?.execSQL(CREATE_TABLE)

        // เพิ่มข้อมูลเข้าตาราง
        val sqlInsert = "INSERT INTO $TABLE_NAME VALUES('650000000-1','Alice','Female',20)"
        db?.execSQL(sqlInsert)
    }

    // Version ไม่ตรงกัน = Drop อันเก่าแสดงอันใหม่
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) { // จะเปลี่ยนโครงสร้าง table ต้องเปลี่ยน version ด้วย
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllStudents():ArrayList<Student>{
        val studentList = ArrayList<Student>()
        val db = readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME",null)
        }catch (e : SQLiteException){
            onCreate(db)
            return ArrayList()  //ค่าที่ได้จากการ query
        }
        var id: String
        var name :String
        var gender :String
        var age :Int
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast){ // isAfterLast อยู่หลังจากตัวสุดท้ายหรือไม่
                id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER))
                age = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE))
                studentList.add(Student(id,name,gender,age)) // เพิ่มข้อมูลลงใน studentList ที่ได้จาก Column ต่างๆ
                cursor.moveToNext()
            }
        }
        db.close()
        return studentList
    }

    //เพิ่มข้อมูล
    fun insertStudent(std:Student):Long{
        val db = writableDatabase
        val value = ContentValues()
        value.put(COLUMN_ID,std.id)
        value.put(COLUMN_NAME,std.name)
        value.put(COLUMN_GENDER,std.gender)
        value.put(COLUMN_AGE,std.age)
        val success = db.insert(TABLE_NAME,null,value) // null คือจะให้ column ไหนเป็นค่าว่างได้บ้าง
        db.close()
        return  success
    }

    //แก้ไขข้อมูล
    fun updateStudent(std:Student):Int{
        val db = writableDatabase
        val value = ContentValues()
        value.put(COLUMN_NAME,std.name)
        value.put(COLUMN_GENDER,std.gender)
        value.put(COLUMN_AGE,std.age)
        val success = db.update(TABLE_NAME,value,"$COLUMN_ID = ?", arrayOf(std.id))
        db.close()
        return success
    }

    //ลบข้อมูล
    fun deleteStudent(std_id:String):Int{
        val db = writableDatabase
        val success = db.delete(TABLE_NAME,"$COLUMN_ID = ?", arrayOf(std_id))
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getStudents(id:String): Student?{
        var student = Student("","","",0)
        val db = readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE id= ?",
                arrayOf(id),null)
        }catch (e : SQLiteException){
            onCreate(db)
            return null
        }
        if(cursor.count == 0) {
            return null
        } else {
            if (cursor.moveToFirst()){
                val id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER))
                val age = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE))
                student = (Student(id,name,gender,age))
            }
        }
        db.close()
        return student
    }
}