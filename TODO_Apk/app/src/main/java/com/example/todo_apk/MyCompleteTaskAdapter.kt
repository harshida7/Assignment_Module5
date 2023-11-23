package com.example.todo_apk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyCompleteTaskAdapter(var context: Context, var listTaskComplete: MutableList<ModelCompletedTask>):BaseAdapter()
{
    override fun getCount(): Int {
        return listTaskComplete.size
    }

    override fun getItem(position: Int): Any {
        return listTaskComplete.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var layoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.design_complete, parent, false)

        var txt_TaskName: TextView = view.findViewById(R.id.txt_TaskNameC)
        var txt_TaskDate: TextView = view.findViewById(R.id.txt_TaskDateC)
        var txt_TaskCate: TextView = view.findViewById(R.id.txt_TaskCatC)

        txt_TaskName.setText(listTaskComplete.get(position).task_namec)
        txt_TaskDate.setText(listTaskComplete.get(position).task_datec)
        txt_TaskCate.setText(listTaskComplete.get(position).task_categoryc)


        return view
    }
}