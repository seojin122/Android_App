package com.example.fianl

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HospitalMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_map)

        val name = intent.getStringExtra("name") ?: "이름 없음"
        val lat = intent.getDoubleExtra("lat", 0.0)
        val lng = intent.getDoubleExtra("lng", 0.0)

        val textView = findViewById<TextView>(R.id.textHospitalInfo)
        textView.text = """
            위치 정보:
            위도: $lat
            경도: $lng
        """.trimIndent()
    }
}
