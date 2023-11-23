package com.example.todo_apk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyDeleteTaskAdapter(var context: Context, var listTaskDelete: MutableList<ModelDeletedTask>):BaseAdapter()
{
    override fun getCount(): Int {
        return listTaskDelete.size
    }

    override fun getItem(position: Int): Any {
        return listTaskDelete.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var layoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.design_delete, parent, false)

        var txt_TaskName: TextView = view.findViewById(R.id.txt_TaskNameD)
        var txt_TaskDate: TextView = view.findViewById(R.id.txt_TaskDateD)
        var txt_TaskCate: TextView = view.findViewById(R.id.txt_TaskCatD)

        txt_TaskName.setText(listTaskDelete.get(position).task_named)
        txt_TaskDate.setText(listTaskDelete.get(position).task_dated)
        txt_TaskCate.setText(listTaskDelete.get(position).task_categoryd)


        return view
    }
}