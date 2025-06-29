package com.example.fianl

import android.content.ContentValues
import android.content.Context

class RecordRepository(context: Context) {

    private val dbHelper = RecordDatabaseHelper(context)

    fun insertRecord(record: Record): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(RecordDatabaseHelper.COLUMN_DATE, record.date)
            put(RecordDatabaseHelper.COLUMN_WEIGHT, record.weight)
            put(RecordDatabaseHelper.COLUMN_STATUS, record.status)
            put(RecordDatabaseHelper.COLUMN_SLEEP, record.sleep)
            put(RecordDatabaseHelper.COLUMN_EXERCISED, if (record.exercised) 1 else 0)
            put(RecordDatabaseHelper.COLUMN_PREGNANT, if (record.pregnant) 1 else 0)
        }
        return db.insert(RecordDatabaseHelper.TABLE_NAME, null, values)
    }

    fun getAllRecords(): List<Record> {
        val records = mutableListOf<Record>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            RecordDatabaseHelper.TABLE_NAME,
            null, null, null, null, null,
            "${RecordDatabaseHelper.COLUMN_DATE} DESC"
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(RecordDatabaseHelper.COLUMN_ID))
                val date = getString(getColumnIndexOrThrow(RecordDatabaseHelper.COLUMN_DATE))
                val weight = getString(getColumnIndexOrThrow(RecordDatabaseHelper.COLUMN_WEIGHT))
                val status = getString(getColumnIndexOrThrow(RecordDatabaseHelper.COLUMN_STATUS))
                val sleep = getString(getColumnIndexOrThrow(RecordDatabaseHelper.COLUMN_SLEEP))
                val exercised = getInt(getColumnIndexOrThrow(RecordDatabaseHelper.COLUMN_EXERCISED)) == 1
                val pregnant = getInt(getColumnIndexOrThrow(RecordDatabaseHelper.COLUMN_PREGNANT)) == 1

                records.add(Record(id, date, weight, status, sleep, exercised, pregnant))
            }
            close()
        }
        return records
    }

    fun hasRecordForDate(date: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            RecordDatabaseHelper.TABLE_NAME,
            arrayOf(RecordDatabaseHelper.COLUMN_ID),
            "${RecordDatabaseHelper.COLUMN_DATE} = ?",
            arrayOf(date),
            null, null, null
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
}
