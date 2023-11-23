package com.example.employeemanagement_m5_t5_6.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.employeemanagement_m5_t5_6.Activity.ProjectUpdateActivity
import com.example.employeemanagement_m5_t5_6.Activity.ProjectViewActivity
import com.example.employeemanagement_m5_t5_6.Model.ProjectModel
import com.example.employeemanagement_m5_t5_6.R


class ProjectAdapter(var context:Context, var list:MutableList<ProjectModel>) :BaseAdapter()
{
    override fun getCount(): Int
    {
        return list.size
    }

    override fun getItem(position: Int): Any
    {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long
    {
        return position.toLong()
    }

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        var layout = LayoutInflater.from(parent!!.context)
        var view = layout.inflate(R.layout.design_project_view,parent,false)

        var txt1:TextView = view.findViewById(R.id.txt1)
        var txt2:TextView = view.findViewById(R.id.txt2)
        var txt3:TextView = view.findViewById(R.id.txt3)
        var img1:ImageView = view.findViewById(R.id.edit)
        var img2:ImageView = view.findViewById(R.id.delete)
        txt1.setText(list.get(position).project_name)
        txt2.setText(list.get(position).project_team_members)
        txt3.setText(list.get(position).project_description)

        img1.setOnClickListener {


            var i = Intent(context,ProjectUpdateActivity::class.java)
            i.putExtra("id",list.get(position).id)
            i.putExtra("pro_name",list.get(position).project_name)
            i.putExtra("pro_team",list.get(position).project_team_members)
            i.putExtra("pro_des",list.get(position).project_description)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)


        }
        img2.setOnClickListener {

            var alert = AlertDialog.Builder(img2.context)
            alert.setTitle("Are you sure you want to delete?")
            alert.setPositiveButton("YES") { dialogInterface: DialogInterface, i: Int ->

                var stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST,
                    "https://compressible-approa.000webhostapp.com/Emp_API/emp_project_delete.php",
                    Response.Listener {

                        Toast.makeText(context, "Product Deleted", Toast.LENGTH_LONG).show()
                        var i = Intent(context, ProjectViewActivity::class.java)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(i)

                    },
                    {
                        Toast.makeText(context, "No Internet", Toast.LENGTH_LONG).show()

                    }) {
                    override fun getParams(): MutableMap<String, String>? {
                        var map = HashMap<String, String>()
                        map["id"] = list.get(position).id.toString()
                        return map
                    }


                }
                var queue: RequestQueue = Volley.newRequestQueue(context)
                queue.add(stringRequest)

            }
            alert.setNegativeButton("NO",{ dialogInterface: DialogInterface, i: Int ->

                dialogInterface.cancel()
            })
            alert.show()
        }

        return view
    }

}