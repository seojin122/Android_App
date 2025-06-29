package com.example.fianl

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_record)

        val dateText = findViewById<TextView>(R.id.textDate)
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date())
        dateText.text = today

        if (SharedPreferencesManager.hasTodayRecord(this)) {
            Toast.makeText(this, "오늘 기록은 이미 작성되었습니다!", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val forceShow = prefs.getBoolean("alwaysShowPregnantUI", false)


        val editWeight = findViewById<EditText>(R.id.editWeight)
        val editStatus = findViewById<EditText>(R.id.editStatus)
        val editSleep = findViewById<EditText>(R.id.editSleep)
        val checkExercise = findViewById<CheckBox>(R.id.checkExercise)
        val checkPregnant = findViewById<CheckBox>(R.id.checkPregnant)
        val layoutExtra = findViewById<LinearLayout>(R.id.layoutPregnantExtra)

        checkPregnant.setOnCheckedChangeListener { _, isChecked ->
            layoutExtra.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        if (forceShow) {
            layoutExtra.visibility = View.VISIBLE
        }

        findViewById<Button>(R.id.btnComplete).setOnClickListener {
            val record = Record(
                date = today,
                weight = editWeight.text.toString(),
                status = editStatus.text.toString(),
                sleep = editSleep.text.toString(),
                exercised = checkExercise.isChecked,
                pregnant = checkPregnant.isChecked
            )
            SharedPreferencesManager.saveRecord(this, record)
            Toast.makeText(this, "기록 완료!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, RecordListActivity::class.java))
            finish()
        }
    }
}
