package com.example.fianl

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecordListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerRecord)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val records = SharedPreferencesManager.getRecords(this).sortedByDescending { it.date }

        if (records.isEmpty()) {
            Toast.makeText(this, "저장된 기록이 없습니다.", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = RecordAdapter(records)
    }
}
