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
    lateinit var imageView: CircleImageView
    lateinit var imageuri: Uri
    lateinit var uploadService: UploadService
    lateinit var btnUpload:Button
    lateinit var txtnm:TextView
    lateinit var txtmob:TextView
    lateinit var txtemail:TextView
    lateinit var btnUpdateProfile:Button


    private val contract = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        imageuri = it!!
        image.setImageURI(it)
        imageView.setImageURI(it)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_details)


        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        sharedPreferencesUser = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

        image = findViewById(R.id.imgShow1)
        imageView = findViewById(R.id.imgProfileView)
        btnUpload = findViewById(R.id.btnUpload)
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile)
        txtnm = findViewById(R.id.txtEmpnm)
        txtmob = findViewById(R.id.txtEmpmob)
        txtemail = findViewById(R.id.txtEmpemail)


        uploadService = ApiClient.getretrofit().create(UploadService::class.java)

        if (sharedPreferencesUser.getBoolean("USER_SESSION", false) && !sharedPreferencesUser.getString("emailuser", "")!!.isEmpty()) {

            val email1 = sharedPreferencesUser.getString("emailuser", "")
            val pass1 = sharedPreferencesUser.getString("userpass", "")

            uploadService= ApiClient.getretrofit()!!.create(UploadService::class.java)

            val intent = intent
            val img = intent.getStringExtra("image1")
            val fname = intent.getStringExtra("nm")
            val phone = intent.getStringExtra("mob")

            Picasso.get().load(img).placeholder(R.drawable.profile).into(imageView)
            txtnm.setText(fname)
            txtemail.setText(email1)
            txtmob.setText(phone)

            Toast.makeText(applicationContext, "Session User  ", Toast.LENGTH_LONG).show()

            var call: Call<Model> = uploadService.viewData(email1,pass1)

        }

        if (sharedPreferences.getBoolean("SESSION", false) && !sharedPreferences.getString("empemail", "")!!.isEmpty()) {

            val email = sharedPreferences.getString("empemail", "1")
            val pass = sharedPreferences.getString("emp_pass", "1")

            uploadService= ApiClient.getretrofit()!!.create(UploadService::class.java)

            val intent = intent
            val img = intent.getStringExtra("image1")
            val fname = intent.getStringExtra("nm")
            val phone = intent.getStringExtra("mob")

            Picasso.get().load(img).placeholder(R.drawable.profile).into(imageView)
            txtnm.setText(fname)
            txtemail.setText(email)
            txtmob.setText(phone)

            Toast.makeText(applicationContext, "Session Admin", Toast.LENGTH_SHORT).show()

            var call: Call<Model> = uploadService.viewData(email,pass)

          /*  call.enqueue(object:Callback<Model>{
                override fun onResponse(call: Call<Model>, response: Response<Model>) {
                    Toast.makeText(applicationContext,"Done", Toast.LENGTH_LONG).show()


                }
                override fun onFailure(call: Call<Model>, t: Throwable) {
                    // Toast.makeText(applicationContext,"No Internet", Toast.LENGTH_LONG).show()
                }
            })
*/
        }

        image.setOnClickListener { contract.launch("image/*") }
        btnUpload.setOnClickListener {

            upload()
            Toast.makeText(applicationContext, "Done", Toast.LENGTH_SHORT).show()
        }




        btnUpdateProfile.setOnClickListener {


            startActivity(Intent(applicationContext,UpdateProfileActivity::class.java))
        }
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

