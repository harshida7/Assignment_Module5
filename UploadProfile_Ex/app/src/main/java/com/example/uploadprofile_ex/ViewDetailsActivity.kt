package com.example.uploadprofile_ex

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.retrofit_crud_ex.ApiClient
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

class ViewDetailsActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferencesUser: SharedPreferences


    lateinit var image: CircleImageView
    lateinit var imageuri: Uri
    lateinit var uploadService: UploadService
    lateinit var btnUpload:Button


    private val contract = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        imageuri = it!!
        image.setImageURI(it)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_details)


        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        sharedPreferencesUser = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

        image = findViewById(R.id.imgShow1)
        btnUpload = findViewById(R.id.btnUpload)
        uploadService = ApiClient.getretrofit().create(UploadService::class.java)


        image.setOnClickListener { contract.launch("image/*") }
        btnUpload.setOnClickListener {

            upload()
            Toast.makeText(applicationContext, "Done", Toast.LENGTH_SHORT).show()
        }
        var call: Call<Model> = uploadService.view()

        call.enqueue(object:Callback<Model>{
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                //Toast.makeText(applicationContext,"Done", Toast.LENGTH_LONG).show()

            }
            override fun onFailure(call: Call<Model>, t: Throwable) {
               // Toast.makeText(applicationContext,"No Internet", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.option,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.logout->
            {
                sharedPreferences.edit().clear().commit()
                sharedPreferencesUser.edit().clear().commit()
                finish()
                startActivity(Intent(applicationContext,UploadProfileActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }



    private fun upload() {
        val filesDir = applicationContext.filesDir
        val file = File(filesDir, "image.png")
        val inputstream = contentResolver.openInputStream(imageuri)
        val outputstream = FileOutputStream(file)
        inputstream!!.copyTo(outputstream)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        val part = MultipartBody.Part.createFormData("img", file.name, requestBody)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://compressible-approa.000webhostapp.com/serverimage/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UploadService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.uploadImage(part, null, null, null, null)
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}

