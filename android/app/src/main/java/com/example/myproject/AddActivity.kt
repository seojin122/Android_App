package com.example.myproject

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myproject.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바에 뒤로가기 기능 구현
        setSupportActionBar(binding.addToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 날짜 구현
        val date = intent.getStringExtra("today")
        binding.date.text = date  // 예: "2025-06-01"
        binding.date.contentDescription = "오늘 날짜는 ${date}입니다"


        // 저장 버튼을 둘렀을 떄 입력값을 반환하다.
        binding.btnSave.setOnClickListener {
            // 데이터 베이스에 저장
            val todo_str = binding.addEditView.text.toString()
            val db = DBHelper(this).writableDatabase
            db.execSQL("insert into TODO_TB(todo) values (?)", arrayOf(todo_str) )
            db.close()

            val intent = intent
            intent.putExtra("result", todo_str)
            setResult(Activity.RESULT_OK, intent)

            finish()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = intent
        intent.putExtra("result", "")
        setResult(Activity.RESULT_OK, intent)

        finish()
        //true
        return super.onSupportNavigateUp()
    }
}

