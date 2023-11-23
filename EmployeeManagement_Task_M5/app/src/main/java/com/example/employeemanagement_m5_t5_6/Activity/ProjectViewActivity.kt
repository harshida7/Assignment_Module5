package com.example.employeemanagement_m5_t5_6.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.employeemanagement_m5_t5_6.Adapter.ProjectAdapter
import com.example.employeemanagement_m5_t5_6.Model.ProjectModel
import com.example.employeemanagement_m5_t5_6.R
import com.example.employeemanagement_m5_t5_6.databinding.ActivityProjectViewBinding
import org.json.JSONArray

class ProjectViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProjectViewBinding
    lateinit var list:MutableList<ProjectModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        list = ArrayList()

        var stringRequest = StringRequest(Request.Method.GET,"https://compressible-approa.000webhostapp.com/Emp_API/emp_view_project.php",
            { response->
            try
            {
                var jsonArray = JSONArray(response)
                for(i in 0 until jsonArray.length())
                {
                    var jsonObject = jsonArray.getJSONObject(i)

                    var id = jsonObject.getInt("id")
                    var pro_name = jsonObject.getString("project_name")
                    var pro_team = jsonObject.getString("project_team_members")
                    var pro_des = jsonObject.getString("project_description")
                    var m = ProjectModel()
                    m.id=id
                    m.project_name=pro_name
                    m.project_team_members = pro_team
                    m.project_description=pro_des
                    list.add(m)

                }
                var adapter = ProjectAdapter(applicationContext,list)
                binding.list.adapter=adapter

            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }
        })
        {
            Toast.makeText(applicationContext,"No Internet", Toast.LENGTH_LONG).show()
        }

        var queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(stringRequest)

    }


}