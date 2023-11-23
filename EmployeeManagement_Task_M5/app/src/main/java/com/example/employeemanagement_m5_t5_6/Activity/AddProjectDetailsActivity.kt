package com.example.employeemanagement_m5_t5_6.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.employeemanagement_m5_t5_6.R
import com.example.employeemanagement_m5_t5_6.databinding.ActivityAddProjectDetailsBinding

class AddProjectDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProjectDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProjectDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnAddProject.setOnClickListener {

            var pro_name = binding.edtProjectName.text.toString()
            var pro_team = binding.edtMember.text.toString()
            var pro_des = binding.edtDescription.text.toString()

            var stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST,"https://compressible-approa.000webhostapp.com/Emp_API/emp_project_insert.php",
                Response.Listener {

                    Toast.makeText(applicationContext,"Project Inserted", Toast.LENGTH_LONG).show()
                    binding.edtProjectName.setText("")
                    binding.edtMember.setText("")
                    binding.edtDescription.setText("")

                    startActivity(Intent(applicationContext, ProjectViewActivity::class.java))
                },
                {
                    Toast.makeText(applicationContext,"No Internet", Toast.LENGTH_LONG).show()

                })
            {
                override fun getParams(): MutableMap<String, String>?
                {
                    var map = HashMap<String,String>()
                    map["project_name"]=pro_name
                    map["project_team_members"]=pro_team
                    map["project_description"]=pro_des

                    return map
                }

            }
            var queue: RequestQueue = Volley.newRequestQueue(this)
            queue.add(stringRequest)

        }



    }
}