package com.example.ch06_view2

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ch06_view2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)
       val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Log.d("tag", "start...")
        binding.progBar.visibility = View.INVISIBLE

        binding.tvMain.text = "View Binding 테스트"

        binding.tvMain.setTextColor(Color.rgb(0,100,150))
    }
}