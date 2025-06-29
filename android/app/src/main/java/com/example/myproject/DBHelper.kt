package com.example.myproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "testdb", null, 1) { // "testdb" 라는 데이터베이스 생성
    override fun onCreate(p0: SQLiteDatabase?) { //테이블 생성
        p0?.execSQL("create table TODO_TB (" +
                "_id integer primary key autoincrement, " +
                "todo TEXT not null)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}