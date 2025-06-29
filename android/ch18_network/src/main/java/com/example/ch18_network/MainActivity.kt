package com.example.ch18_network

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ch18_network.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var jsonfragment = JsonFragment()
        var xmlfragment = XmlFragment()
        var imgfragment = ImgFragment()

        val bundle = Bundle()

        binding.btnSearch.setOnClickListener {
            val loc = binding.edtLoc.text.toString()
            if(loc == ""){
                Toast.makeText(this, "지역 ID를 입력하세요. 예: 서울은 108, 제주는 184", Toast.LENGTH_SHORT).show()
            }
            else {
                bundle.putString("searchLoc", binding.edtLoc.text.toString())

                if (binding.rGroup.checkedRadioButtonId == R.id.rbJson) {
                    jsonfragment = JsonFragment()
                    jsonfragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_content, jsonfragment)
                        .commit()
                } else if (binding.rGroup.checkedRadioButtonId == R.id.rbXml) {
                    xmlfragment = XmlFragment()
                    xmlfragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_content, xmlfragment)
                        .commit()
                } else if (binding.rGroup.checkedRadioButtonId == R.id.rbImg) {
                    imgfragment = ImgFragment()
                    imgfragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_content, imgfragment)
                        .commit()
                }
            }
        }
    }
}