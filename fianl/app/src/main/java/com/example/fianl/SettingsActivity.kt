package com.example.fianl

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)

        val switchNotification = findViewById<Switch>(R.id.switchNotification)
        val switchDarkMode = findViewById<Switch>(R.id.switchDarkMode)
        val switchAutoSave = findViewById<Switch>(R.id.switchAutoSave)
        val switchAlwaysShowPregnant = findViewById<Switch>(R.id.switchAlwaysShowPregnant)


        switchNotification.isChecked = prefs.getBoolean("isNotificationOn", true)
        switchDarkMode.isChecked = prefs.getBoolean("darkModeEnabled", false)
        switchAutoSave.isChecked = prefs.getBoolean("autoSaveEnabled", true)
        switchAlwaysShowPregnant.isChecked = prefs.getBoolean("alwaysShowPregnantUI", false)


        switchNotification.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("isNotificationOn", isChecked).apply()
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("darkModeEnabled", isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        switchAutoSave.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("autoSaveEnabled", isChecked).apply()
        }

        switchAlwaysShowPregnant.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("alwaysShowPregnantUI", isChecked).apply()
        }
    }
}
