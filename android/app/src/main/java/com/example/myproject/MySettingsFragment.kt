package com.example.myproject

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MySettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // XML로부터 설정 화면 생성
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        // ID 설정 요약 처리
        val idPreference = findPreference<EditTextPreference>("ID")
        idPreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (text.isNullOrEmpty()) {
                "ID 설정이 되지 않았습니다."
            } else {
                "설정된 ID는 $text"
            }
        }

        // 색상 설정 요약 처리 (기본 제공 SummaryProvider 사용)
        val colorPreference = findPreference<ListPreference>("color")
        colorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
    }
}
