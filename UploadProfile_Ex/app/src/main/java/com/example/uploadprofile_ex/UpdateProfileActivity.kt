package com.example.uploadprofile_ex

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

class UpdateProfileActivity : AppCompatActivity() {
    lateinit var btnsubmit: Button
    lateinit var image: CircleImageView
    lateinit var imageuri: Uri
    lateinit var name: EditText
    lateinit var mobile: EditText
    lateinit var email: EditText
    lateinit var pass: EditText


    private val contract = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        imageuri = it!!
        image.setImageURI(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        name = findViewById(R.id.name_update)
        mobile = findViewById(R.id.mobile_update)
        email = findViewById(R.id.email_update)
        pass = findViewById(R.id.pass_update)
        image = findViewById(R.id.img)

        image.setOnClickListener { contract.launch("image/*") }
        btnsubmit = findViewById(R.id.btnUpdateData)

        btnsubmit.setOnClickListener {
            upload()
            var i = Intent(applicationContext,ViewDetailsActivity::class.java)

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
            RequestBody.Companion.create(MultipartBody.FORM, name.text.toString())
        val mobile1: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, mobile.text.toString())
        val email1: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, email.text.toString())
        val pass1: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, pass.text.toString())

        val retrofit = Retrofit.Builder()
            .baseUrl("https://compressible-approa.000webhostapp.com/serverimage/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UploadService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.uploadImage(part, name1, mobile1, email1, pass1)
        }


    }

}