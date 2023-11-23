package com.example.employeemanagement_m5_t5_6.Activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.employeemanagement_m5_t5_6.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler().postDelayed(Runnable {

            startActivity(Intent(applicationContext, EmployeeRegisterActivity::class.java))
        },3000)
    }
}