package com.example.todo_apk

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar


class UpcomingTaskFragment : Fragment() {

    lateinit var dbHelper: DbHelper
    lateinit var layout1: FrameLayout
    lateinit var listView: ListView
    lateinit var listTask:MutableList<Model>


    var mc = ModelCompletedTask()
    var md = ModelDeletedTask()

    @SuppressLint("MissingInflatedId", "UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_upcoming_task, container, false)


        dbHelper = DbHelper(requireActivity())

        listTask = ArrayList()
        var m = Model()

        listView = view.findViewById(R.id.listTask)
        layout1 = view.findViewById(R.id.layoutupcoming)

        listTask = dbHelper.viewdata()

            var adapter = MyAdapter(requireActivity(), listTask)
            listView.adapter = adapter

            adapter.notifyDataSetChanged()

            listView.setOnCreateContextMenuListener(this)

        if(listTask.isEmpty())
        {
            Snackbar.make(layout1,"Please Add Task", Snackbar.LENGTH_SHORT).show()
        }
        return view
    }


    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?)
    {

            var m1: MenuItem = menu!!.add(0,1,0,"Complete Task")
            var m2: MenuItem = menu!!.add(0,2,0,"Update Task")
            var m3: MenuItem = menu!!.add(0,3,0,"Delete Task")

        super.onCreateContextMenu(menu, v, menuInfo)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean
    {
        var acm: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        var pos = acm.position
        var m = listTask[pos]

        val bundle = Bundle()
        bundle.putString("taskname", m.task_name)
        bundle.putString("taskdate", m.task_date)
        bundle.putString("taskcate", m.task_category)

        when(item.itemId)
        {
            1->

            {

                var completedTaskFragment = CompletedTaskFragment()
                var fm: FragmentManager = requireFragmentManager()
                var ft: FragmentTransaction = fm.beginTransaction()

                var nm = bundle?.getString("taskname").toString()
                var date = bundle?.getString("taskdate").toString()
                var cate = bundle?.getString("taskcate").toString()

                mc.task_namec = nm
                mc.task_datec = date
                mc.task_categoryc = cate

                completedTaskFragment.setArguments(bundle)

                dbHelper.insertCompleteTaskdata(mc)

                ft.replace(R.id.frmDisplayData,completedTaskFragment).commit()

                dbHelper.deletedata(m.id)

                Snackbar.make(layout1, "Task Completed", Snackbar.LENGTH_SHORT).show()
            }

            2->
            {
                var i = Intent(requireContext(), UpdateActivity::class.java)
                i.putExtra("id",m.id)
                i.putExtra("tn",m.task_name)
                i.putExtra("td",m.task_date)
                i.putExtra("tc",m.task_category)
                startActivity(i)
                dbHelper.deletedata(m.id)
            }
            3->
            {
                var alert = AlertDialog.Builder(requireActivity())
                alert.setTitle("Are you Sure you want to delete?")
                alert.setPositiveButton("YES") { dialogInterface: DialogInterface, i: Int ->

                    dbHelper.deletedata(m.id)

                    var removedTaskFragment = RemovedTaskFragment()
                    var fm: FragmentManager = requireFragmentManager()
                    var ft: FragmentTransaction = fm.beginTransaction()

                    var nm = bundle?.getString("taskname").toString()
                    var date = bundle?.getString("taskdate").toString()
                    var cate = bundle?.getString("taskcate").toString()

                    md.task_named = nm
                    md.task_dated = date
                    md.task_categoryd = cate

                    removedTaskFragment.setArguments(bundle)


                    dbHelper.insertDeleteData(md)

                    ft.replace(R.id.frmDisplayData,removedTaskFragment).commit()

                    Snackbar.make(layout1, "Task Removed", Snackbar.LENGTH_SHORT).show()

                }
                alert.setNegativeButton("NO") { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.cancel()
                }
                alert.show()

            }
        }

        return super.onContextItemSelected(item)
    }



}