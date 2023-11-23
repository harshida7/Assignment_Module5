package com.example.employeemanagement_m5_t5_6.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.employeemanagement_m5_t5_6.R
import com.example.employeemanagement_m5_t5_6.databinding.ActivityEmployeeLoginBinding
import com.example.employeemanagement_m5_t5_6.databinding.ActivityEmployeeRegisterBinding
import org.json.JSONException

class EmployeeLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeLoginBinding
    /*lateinit var sharedPreferences: SharedPreferences*/
    lateinit var sharedPreferencesUser: SharedPreferences
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_login)
        binding = ActivityEmployeeLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferencesUser = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

        if(sharedPreferencesUser.getBoolean("USER_SESSION",false) && !sharedPreferencesUser.getString("email","")!!.isEmpty())
        {
            startActivity(Intent(this, AddProjectActivity::class.java))
            finish()
        }

        toolbar = findViewById(R.id.tool1)
        setSupportActionBar(toolbar)

        val DarkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = DarkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

        if(isDarkModeOn){
            binding.cardLogin.setCardBackgroundColor(Color.parseColor("#282828"))
            binding.linearLogin.setBackgroundColor(Color.parseColor("#121212"))
            toolbar.setBackgroundColor(Color.parseColor("#282828"))
            toolbar.setTitleTextColor(Color.WHITE)
        }
        else{
            // binding.linearRegister1.setBackgroundColor(Color.parseColor("#ffe5ec"))
            binding.cardLogin.setCardBackgroundColor(Color.parseColor("#ffe5ec"))
            toolbar.setBackgroundColor(Color.parseColor("#ffe5ec"))
            toolbar.setTitleTextColor(Color.BLACK)
        }

        binding.btnLogin.setOnClickListener {

            var email = binding.edtEmail.text.toString()
            var pass = binding.edtPassword.text.toString()

            var stringrequest: StringRequest = object: StringRequest(
                Request.Method.POST,"https://compressible-approa.000webhostapp.com/Emp_API/emp_login.php",{
                    response->
                try
                {
                    if(response.trim().equals("0"))
                    {
                        Toast.makeText(applicationContext,"Login Fail", Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"Login Success", Toast.LENGTH_LONG).show()
                        var editor: SharedPreferences.Editor = sharedPreferencesUser.edit()
                        editor.putBoolean("USER_SESSION",true)
                        editor.putString("email",email)
                        editor.putString("pass",pass)
                        editor.commit()
                        startActivity(Intent(applicationContext,AddProjectActivity::class.java))
                    }
                }
                catch(e: JSONException)
                {
                    e.printStackTrace()
                }

            },
                {
                    Toast.makeText(applicationContext,"No Internet", Toast.LENGTH_LONG).show()
                })
            {
                override fun getParams(): MutableMap<String, String>?
                {
                    var map = HashMap<String,String>()
                    map["email"]=email
                    map["password"]=pass
                    return map
                }
            }

            var queue: RequestQueue = Volley.newRequestQueue(this)
            queue.add(stringrequest)

        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}

