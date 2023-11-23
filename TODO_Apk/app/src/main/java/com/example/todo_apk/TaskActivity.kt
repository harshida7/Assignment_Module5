package com.example.todo_apk

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar

class TaskActivity : AppCompatActivity() {

    lateinit var edtTaskAdd:EditText

    lateinit var floatingActionButton: FloatingActionButton
    lateinit var Linear1: LinearLayout
    lateinit var SpinList: Spinner
    lateinit var dbHelper: DbHelper
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var  toolbar: Toolbar

companion object
{
    lateinit var edtTaskDate:EditText
}
    var arr = arrayOf("Default", "Personal", "Shopping", "Wishlist", "Work")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        edtTaskAdd = findViewById(R.id.edtTask)
        edtTaskDate = findViewById(R.id.edtTaskDate)
        floatingActionButton = findViewById(R.id.f1)
        Linear1 = findViewById(R.id.l1)
        SpinList = findViewById(R.id.spinnerList)
        dbHelper = DbHelper(applicationContext)
        toolbar = findViewById(R.id.tool1)

        setSupportActionBar(toolbar)

        val adap =
            ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, arr)

        SpinList.adapter = adap

        var upcomingTaskFragment = UpcomingTaskFragment()
        var fm: FragmentManager = supportFragmentManager
        var ft: FragmentTransaction = fm.beginTransaction()


        SpinList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {



            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                floatingActionButton.setOnClickListener {

                    var m = Model()


                    var taskNm = edtTaskAdd.text.toString()
                    var taskDt = edtTaskDate.text.toString()

                    if (taskNm.toString().length == 0 && taskDt.toString().length == 0) {
                        Toast.makeText(applicationContext, "Enter Valid Details", Toast.LENGTH_SHORT).show()
                    } else {
                        m.task_name = taskNm
                        m.task_date = taskDt


                        when (position) {
                            1 -> { m.task_category = "Personal" }
                            2 -> { m.task_category = "Shopping" }
                            3 -> { m.task_category = "Wishlist" }
                            4 -> { m.task_category = "Work" }
                            else -> { m.task_category = "Default" }
                        }
                        var data = dbHelper.insertdata(m)

                        Toast.makeText(applicationContext, "Record" + data, Toast.LENGTH_SHORT).show()



                    val bundle = Bundle()
                    bundle.putString("tasknm", taskNm)
                    bundle.putString("taskdate", taskDt)
                    bundle.putString("taskcate", m.task_category)

                    upcomingTaskFragment.arguments = bundle
                    ft.add(R.id.frmDisplayData, upcomingTaskFragment).commit()

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
                edtTaskDate.setOnClickListener {

                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)

                    val datePickerDialog = DatePickerDialog(this,
                        { view, year, monthOfYear, dayOfMonth ->

                            edtTaskDate.setText(dayOfMonth.toString() + "/ " + (monthOfYear + 1).toString() + "/ " + year)
                        },
                        year,
                        month,
                        day
                    )

                    datePickerDialog.show()
                }
        }

    }




