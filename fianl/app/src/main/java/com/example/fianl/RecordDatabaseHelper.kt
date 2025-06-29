package com.example.fianl

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecordDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "record.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "records"
        const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date"
        const val COLUMN_WEIGHT = "weight"
        const val COLUMN_STATUS = "status"
        const val COLUMN_SLEEP = "sleep"
        const val COLUMN_EXERCISED = "exercised"
        const val COLUMN_PREGNANT = "pregnant"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_WEIGHT TEXT,
                $COLUMN_STATUS TEXT,
                $COLUMN_SLEEP TEXT,
                $COLUMN_EXERCISED INTEGER,
                $COLUMN_PREGNANT INTEGER
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
