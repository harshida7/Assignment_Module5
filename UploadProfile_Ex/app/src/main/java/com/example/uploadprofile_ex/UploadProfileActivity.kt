package com.example.uploadprofile_ex

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

class UploadProfileActivity : AppCompatActivity() {
    lateinit var btnsubmit: Button
    lateinit var image: CircleImageView
    lateinit var imageuri: Uri
    lateinit var edt1: EditText
    lateinit var edt2: EditText
    lateinit var edt3: EditText
    lateinit var edt4: EditText
    lateinit var txtLogin : TextView

    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferencesUser: SharedPreferences

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        imageuri = it!!
        image.setImageURI(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_profile)

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
        txtLogin = findViewById(R.id.userLoginTxt)
        edt1 = findViewById(R.id.name)
        edt2 = findViewById(R.id.mobile)
        edt3 = findViewById(R.id.email)
        edt4 = findViewById(R.id.pass)
        image = findViewById(R.id.img)

        image.setOnClickListener { contract.launch("image/*") }
        btnsubmit = findViewById(R.id.btnRegister1)

        btnsubmit.setOnClickListener {
            upload()
            var i = Intent(applicationContext,ViewDetailsActivity::class.java)
            i.putExtra("nm", edt1.text.toString())
            i.putExtra("mob", edt2.text.toString())
            i.putExtra("image1", imageuri.toString())

            startActivity(i)

        }

        val str = txtLogin.text.toString()
        val spannstr = SpannableString(str)

        spannstr.setSpan(UnderlineSpan(), 0, spannstr.length, 0)
        txtLogin.text = spannstr

        txtLogin.setOnClickListener {


            var i = Intent(applicationContext,LoginActivity::class.java)
            startActivity(i)
        }

    }

    private fun upload() {
        val filesDir = applicationContext.filesDir
        val file = File(filesDir, "image.png")
        val inputstream = contentResolver.openInputStream(imageuri)
        val outputstream = FileOutputStream(file)
        inputstream!!.copyTo(outputstream)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        val part = MultipartBody.Part.createFormData("img", file.name, requestBody)

        val name1: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, edt1.text.toString())
        val mobile1: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, edt2.text.toString())
        val email1: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, edt3.text.toString())
        val pass1: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, edt4.text.toString())

        val retrofit = Retrofit.Builder()
            .baseUrl("https://compressible-approa.000webhostapp.com/serverimage/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UploadService::class.java)


        var editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("SESSION", true)
        editor.putString("empemail", edt3.text.toString())
        editor.putString("emp_pass", pass1.toString())
        editor.commit()



        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.uploadImage(part, name1, mobile1, email1, pass1)
        }

    }



    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
