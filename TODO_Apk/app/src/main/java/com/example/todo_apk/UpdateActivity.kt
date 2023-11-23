package com.example.todo_apk

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar

class UpdateActivity : AppCompatActivity() {

    lateinit var dbHelper: DbHelper
    lateinit var edt1: EditText

    lateinit var SpinList: Spinner
    lateinit var floatingActionButton: FloatingActionButton

    companion object {
        lateinit var edt2: EditText
    }

    var arr = arrayOf("Default", "Personal", "Shopping", "Wishlist", "Work")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        edt1 = findViewById(R.id.edtTaskUpdate)
        edt2 = findViewById(R.id.edtTaskDateUpdate)
        SpinList = findViewById(R.id.spinnerList)

        floatingActionButton = findViewById(R.id.f1)


        val i1 = intent
        var id1 = i1.getIntExtra("id", 101)
        var nm = i1.getStringExtra("tn")
        var dt = i1.getStringExtra("td")
        var cat = i1.getStringExtra("tc")

        edt1.setText(nm)
        edt2.setText(dt)



        val adap =ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, arr)

        SpinList.adapter = adap

        dbHelper = DbHelper(applicationContext)

        SpinList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                floatingActionButton.setOnClickListener {


                    var taskNm = edt1.text.toString()
                    var taskDt = edt2.text.toString()

                    var m = ModelUpdate()

                    m.idu = id1
                    m.task_nameu = taskNm
                    m.task_dateu = taskDt

                    when (position) {
                        1 -> { m.task_categoryu = "Personal" }

                        2 -> { m.task_categoryu = "Shopping" }

                        3 -> { m.task_categoryu = "Wishlist" }

                        4 -> { m.task_categoryu = "Work" }

                        else -> { m.task_categoryu = "Default" }
                    }

                    var data1 = dbHelper.insertUpdatedata(m)

                    Toast.makeText(applicationContext, "1 inserted in update " + data1, Toast.LENGTH_SHORT).show()


                    var updatedTaskFragment= UpdatedTaskFragment()
                    var fm: FragmentManager = supportFragmentManager
                    var ft: FragmentTransaction = fm.beginTransaction()
                    ft.replace(R.id.frmDisplayData,updatedTaskFragment).commit()

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        edt2.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth ->

                    edt2.setText(dayOfMonth.toString() + "/ " + (monthOfYear + 1).toString() + "/ " + year)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }
}