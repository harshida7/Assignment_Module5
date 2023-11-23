package com.example.employeemanagement_m5_t5_6.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeemanagement_m5_t5_6.Adapter.MyAdapter
import com.example.employeemanagement_m5_t5_6.Model.DashboardModel
import com.example.employeemanagement_m5_t5_6.R

class AddProjectActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferencesUser: SharedPreferences
    lateinit var recyclerView: RecyclerView
    lateinit var list:MutableList<DashboardModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)


        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        sharedPreferencesUser = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)


        recyclerView = findViewById(R.id.recycler)
        list = ArrayList()


        var manager : RecyclerView.LayoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager=manager

        list.add(DashboardModel(R.drawable.add,"Add Project"))
        list.add(DashboardModel(R.drawable.view,"View Project Details"))


        var myAdapter = MyAdapter(applicationContext,list)
        recyclerView.adapter=myAdapter
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
                startActivity(Intent(applicationContext,EmployeeRegisterActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}