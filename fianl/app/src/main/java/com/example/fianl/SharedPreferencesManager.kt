package com.example.fianl

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesManager {
    private const val PREF_NAME = "record_prefs"
    private const val KEY_RECORDS = "records"

    fun saveRecord(context: Context, record: Record) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val existing = getRecords(context).toMutableList()
        existing.removeAll { it.date == record.date }
        existing.add(record)
        prefs.edit().putString(KEY_RECORDS, Gson().toJson(existing)).apply()
    }

    fun getRecords(context: Context): List<Record> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_RECORDS, null) ?: return emptyList()
        val type = object : TypeToken<List<Record>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun hasTodayRecord(context: Context): Boolean {
        val today = getToday()
        return getRecords(context).any { it.date == today }
    }

    private fun getToday(): String {
        return java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA)
            .format(java.util.Date())
    }

    fun isAutoSaveEnabled(context: Context): Boolean {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getBoolean("autoSaveEnabled", true)
    }
}
