package com.example.honcook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.honcook.databinding.ActivityMainBinding
import com.example.honcook.databinding.MainpageBinding

class MainActivity : AppCompatActivity() {
    private lateinit var loginbinding: ActivityMainBinding
    private lateinit var mainbinding: MainpageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginbinding = ActivityMainBinding.inflate(layoutInflater)
        mainbinding = MainpageBinding.inflate(layoutInflater)
        val loginview = loginbinding.root
        val mainview = mainbinding.root
//        setContentView(R.layout.activity_main)
        setContentView(loginview)
        loginbinding.loginbutton.setOnClickListener{
            setContentView(mainview)
        }
        mainbinding.logoutbutton.setOnClickListener{
            setContentView(loginview)
        }
    }
}