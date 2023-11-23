package com.example.todo_apk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyAdapter(var context: Context, var listTask: MutableList<Model>):BaseAdapter()
{
    override fun getCount(): Int {
        return listTask.size
    }

    override fun getItem(position: Int): Any {
        return listTask.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var layoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.design, parent, false)

        var txt_TaskName: TextView = view.findViewById(R.id.txt_TaskName)
        var txt_TaskDate: TextView = view.findViewById(R.id.txt_TaskDate)
        var txt_TaskCate: TextView = view.findViewById(R.id.txt_TaskCat)

        txt_TaskName.setText(listTask.get(position).task_name)
        txt_TaskDate.setText(listTask.get(position).task_date)
        txt_TaskCate.setText(listTask.get(position).task_category)


        return view
    }
}