package com.example.todo_apk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyUpdateAdapter(var context: Context, var listTaskUpdate: MutableList<ModelUpdate>):BaseAdapter()
{
    override fun getCount(): Int {
        return listTaskUpdate.size
    }

    override fun getItem(position: Int): Any {
        return listTaskUpdate.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var layoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.design_update, parent, false)

        var txt_TaskName: TextView = view.findViewById(R.id.txt_TaskNameU)
        var txt_TaskDate: TextView = view.findViewById(R.id.txt_TaskDateU)
        var txt_TaskCate: TextView = view.findViewById(R.id.txt_TaskCatU)

        txt_TaskName.setText(listTaskUpdate.get(position).task_nameu)
        txt_TaskDate.setText(listTaskUpdate.get(position).task_dateu)
        txt_TaskCate.setText(listTaskUpdate.get(position).task_categoryu)


        return view
    }
}