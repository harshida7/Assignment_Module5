package com.example.employeemanagement_m5_t5_6.Adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employeemanagement_m5_t5_6.Activity.AddProjectActivity
import com.example.employeemanagement_m5_t5_6.Activity.AddProjectDetailsActivity
import com.example.employeemanagement_m5_t5_6.Activity.ProjectViewActivity
import com.example.employeemanagement_m5_t5_6.Model.DashboardModel
import com.example.employeemanagement_m5_t5_6.R


//Press alt+Enter
class MyAdapter(var context:Context,var list:MutableList<DashboardModel>) : RecyclerView.Adapter<MyView>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView
    {
        var inflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.design_dashboard,parent,false)
        return MyView(view)
    }
    override fun onBindViewHolder(holder: MyView, position: Int)
    {
        holder.image.setImageResource(list.get(position).image)
        holder.txt.setText(list.get(position).text)


        holder.itemView.setOnClickListener{

            if(position == 0)
            {
                val myactivity = Intent(context.applicationContext, AddProjectDetailsActivity::class.java)
                myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK)
                context.applicationContext.startActivity(myactivity)
            }

            if(position == 1)
            {
                val myactivity = Intent(context.applicationContext, ProjectViewActivity::class.java)
                myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK)
                context.applicationContext.startActivity(myactivity)
            }

        }

    }
    override fun getItemCount(): Int
    {
        return list.size
    }

}
class MyView(itemview: View) : RecyclerView.ViewHolder(itemview) {

    var image: ImageView = itemview.findViewById(R.id.img)
    var txt: TextView = itemview.findViewById(R.id.txt1)
}