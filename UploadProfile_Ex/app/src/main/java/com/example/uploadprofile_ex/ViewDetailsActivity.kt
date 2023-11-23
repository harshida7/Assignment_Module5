package com.example.uploadprofile_ex

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.retrofit_crud_ex.ApiClient
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewDetailsActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferencesUser: SharedPreferences

    lateinit var imageView: CircleImageView

    lateinit var uploadService: UploadService
    lateinit var txtnm:TextView
    lateinit var txtmob:TextView
    lateinit var txtemail:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_details)


        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        sharedPreferencesUser = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

        imageView = findViewById(R.id.imgShow1)
        txtnm = findViewById(R.id.txtEmpNm)
        txtmob = findViewById(R.id.txtEmpMob)
        txtemail = findViewById(R.id.txtEmpEmail)

        uploadService = ApiClient.getretrofit().create(UploadService::class.java)

        val i = intent

        val img = i.getStringExtra("img1")
        val nm = i.getStringExtra("emp_name")
        val mob = i.getStringExtra("emp_mobile")
        val email = i.getStringExtra("emp_email")

        txtnm.setText(nm)
        txtmob.setText(mob)
        txtemail.setText(email)
       Picasso.get().load(img).placeholder(R.drawable.profile).into(imageView)
    //imageView.setImageResource(i.getIntExtra("img1", 0))

        var call: Call<Model> = uploadService.view()

        call.enqueue(object:Callback<Model>{
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                Toast.makeText(applicationContext,"Done", Toast.LENGTH_LONG).show()
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
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}

