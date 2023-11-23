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
import com.example.employeemanagement_m5_t5_6.databinding.ActivityProjectUpdateBinding

class ProjectUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProjectUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectUpdateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var i = intent
        var id = i.getIntExtra("id",101)
        binding.edtProjectName.setText(i.getStringExtra("pro_name"))
        binding.edtMember.setText(i.getStringExtra("pro_team"))
        binding.edtDescription.setText(i.getStringExtra("pro_des"))

        binding.btnUpdateProject.setOnClickListener {

            var pro_name = binding.edtProjectName.text.toString()
            var pro_team = binding.edtMember.text.toString()
            var pro_des = binding.edtDescription.text.toString()

            var stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST,"https://compressible-approa.000webhostapp.com/Emp_API/emp_project_update.php",
                Response.Listener {

                    Toast.makeText(applicationContext,"Product Updated", Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext,ProjectViewActivity::class.java))

                },
                {
                    Toast.makeText(applicationContext,"No Internet", Toast.LENGTH_LONG).show()

                })
            {
                override fun getParams(): MutableMap<String, String>?
                {
                    var map = HashMap<String,String>()
                    map["id"]=id.toString()
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