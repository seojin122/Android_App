package com.example.fianl

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CommunityWriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_write)

        val etTitle = findViewById<EditText>(R.id.editTitle)
        val etContent = findViewById<EditText>(R.id.editContent)
        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val t = etTitle.text.toString().trim()
            val c = etContent.text.toString().trim()
            if (t.isEmpty() || c.isEmpty()) return@setOnClickListener

            FirestoreCommunityManager.savePost(
                Post(title = t, content = c),
                onSuccess = {
                    Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show()
                    finish()
                },
                onFailure = { e ->
                    Toast.makeText(this, "저장 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
