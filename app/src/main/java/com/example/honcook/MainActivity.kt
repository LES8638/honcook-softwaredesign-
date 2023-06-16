package com.example.honcook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.honcook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
//        setContentView(R.layout.activity_main)
        setContentView(view)
        var count=0
        binding.button1.setOnClickListener{
            count++
            binding.text01.text = count.toString()
            binding.button1.text = "inc count"

        }
    }
}