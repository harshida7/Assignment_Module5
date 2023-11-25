package com.example.uploadprofile_ex

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.retrofit_crud_ex.ApiClient
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    lateinit var uploadService: UploadService
    lateinit var edtEmail :EditText
    lateinit var edtPass :EditText
    lateinit var btnLogin :Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferencesUser: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        sharedPreferencesUser = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("SESSION", false) && !sharedPreferences.getString("empemail", "")!!.isEmpty()) {
            startActivity(Intent(applicationContext, ViewDetailsActivity::class.java))
            finish()
        }
        if (sharedPreferencesUser.getBoolean("USER_SESSION", false) && !sharedPreferencesUser.getString("emailuser", "")!!.isEmpty()) {
            startActivity(Intent(applicationContext, ViewDetailsActivity::class.java))
            finish()
        }

        edtEmail = findViewById(R.id.edtEmail)
        edtPass = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {

            var email = edtEmail.text.toString()
            var pass = edtPass.text.toString()
            uploadService = ApiClient.getretrofit().create(UploadService::class.java)

            val call:Call<Model> = uploadService.logindata(email,pass)

        call.enqueue(object: Callback<Model>
        {

            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                var i = Intent(applicationContext,ViewDetailsActivity::class.java)
                startActivity(i)
                val call:Call<Model> = uploadService.viewData(email,pass)
                }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }

        })
            var editor: SharedPreferences.Editor = sharedPreferencesUser.edit()
            editor.putBoolean("USER_SESSION",true)
            editor.putString("emailuser",email)
            editor.putString("passuser",pass)
            editor.commit()
    }

    }
}

