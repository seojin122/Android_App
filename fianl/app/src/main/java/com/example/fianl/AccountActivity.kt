package com.example.fianl

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class AccountActivity : AppCompatActivity() {

    private lateinit var editNickname: EditText
    private lateinit var editPhone: EditText
    private lateinit var editEmail: EditText
    private lateinit var editBirth: EditText
    private lateinit var btnSave: Button
    private lateinit var textLogout: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        editNickname = findViewById(R.id.editNickname)
        editPhone = findViewById(R.id.editPhone)
        editEmail = findViewById(R.id.editEmail)
        editBirth = findViewById(R.id.editBirth)
        btnSave = findViewById(R.id.btnSave)
        textLogout = findViewById(R.id.textLogout)

        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        editNickname.setText(sharedPref.getString("nickname", ""))
        editPhone.setText(sharedPref.getString("phone", ""))
        editEmail.setText(sharedPref.getString("email", ""))
        editBirth.setText(sharedPref.getString("birth", ""))

        btnSave.setOnClickListener {
            val nickname = editNickname.text.toString()
            val phone = editPhone.text.toString()
            val email = editEmail.text.toString()
            val birth = editBirth.text.toString()

            sharedPref.edit()
                .putString("nickname", nickname)
                .putString("phone", phone)
                .putString("email", email)
                .putString("birth", birth)
                .apply()

            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
        }

        textLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            sharedPref.edit().putBoolean("isLoggedIn", false).apply()

            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
