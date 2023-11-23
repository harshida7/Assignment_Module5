package com.example.todo_apk

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class AddTaskActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {


   lateinit var  f1:FloatingActionButton
   lateinit var tabLayout: TabLayout



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        tabLayout = findViewById(R.id.tabLayout)
        f1 = findViewById(R.id.f1)
        tabLayout.addOnTabSelectedListener(this)


        f1.setOnClickListener {
            startActivity(Intent(applicationContext, TaskActivity::class.java))
        }

    }



    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab!!.position)
        {

            0->
            {
                var upcomingTaskFragment = UpcomingTaskFragment()
                var fm: FragmentManager = supportFragmentManager
                var ft: FragmentTransaction = fm.beginTransaction()
                ft.replace(R.id.frmDisplayData,upcomingTaskFragment).commit()
            }

            1->
            {
                var updatedTaskFragment = UpdatedTaskFragment()
                var fm: FragmentManager = supportFragmentManager
                var ft: FragmentTransaction = fm.beginTransaction()
                ft.replace(R.id.frmDisplayData,updatedTaskFragment).commit()
            }

            2->
            {
                var removedTaskFragment = RemovedTaskFragment()
                var fm: FragmentManager = supportFragmentManager
                var ft: FragmentTransaction = fm.beginTransaction()
                ft.replace(R.id.frmDisplayData,removedTaskFragment).commit()
            }

            3->
            {
                var completedTaskFragment = CompletedTaskFragment()
                var fm: FragmentManager = supportFragmentManager
                var ft: FragmentTransaction = fm.beginTransaction()
                ft.replace(R.id.frmDisplayData,completedTaskFragment).commit()
            }

        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }


}

