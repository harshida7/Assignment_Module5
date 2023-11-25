    package com.example.uploadprofile_ex

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

    class MainActivity : AppCompatActivity() {
        lateinit var cardView: CardView
        lateinit var img1: ImageView
        lateinit var txt1: TextView

        lateinit var sharedPreferences: SharedPreferences
        lateinit var sharedPreferencesUser: SharedPreferences

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)
            sharedPreferencesUser = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

            if (sharedPreferences.getBoolean("SESSION", false) && !sharedPreferences.getString("empemail", "")!!.isEmpty()) {
                var i = Intent(applicationContext,ViewDetailsActivity::class.java)

                startActivity(i)
                finish()
            }
            if (sharedPreferencesUser.getBoolean("USER_SESSION", false) && !sharedPreferencesUser.getString("emailuser", "")!!.isEmpty()) {
                startActivity(Intent(applicationContext, ViewDetailsActivity::class.java))
                finish()
            }
            cardView = findViewById(R.id.card1)
            img1 = findViewById(R.id.img1)
            txt1 = findViewById(R.id.txt1)


            cardView.setOnClickListener {

                startActivity(Intent(applicationContext,UploadProfileActivity::class.java))

                var editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putBoolean("SESSION", true)
                editor.putString("empemail", "")
                editor.putString("emp_pass", "")
                editor.commit()



            }

        }

        override fun onBackPressed() {
            super.onBackPressed()
            finishAffinity()
        }
    }