package com.example.todo_apk

import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class RemovedTaskFragment : Fragment() {
    lateinit var dbHelper: DbHelper
    lateinit var listView: ListView
    lateinit var listComplete:MutableList<ModelDeletedTask>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_removed_task, container, false)

        var m = ModelDeletedTask()
        dbHelper = DbHelper(requireActivity())

        listComplete = ArrayList()
        listView = view.findViewById(R.id.listDelete)

        listComplete = dbHelper.deletetask_viewdata()

        var adapter = MyDeleteTaskAdapter(requireActivity(), listComplete)
        listView.adapter = adapter

        adapter.notifyDataSetChanged()
        listView.setOnCreateContextMenuListener(this)

        return view
    }
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?)
    {

        var m1: MenuItem = menu!!.add(0,1,0,"Delete Task")

        super.onCreateContextMenu(menu, v, menuInfo)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean
    {
        var acm: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        var pos = acm.position
        var m = listComplete[pos]

        when(item.itemId)
        {

            1->
            {
                var alert = AlertDialog.Builder(requireActivity())
                alert.setTitle("Are you Sure you want to delete?")
                alert.setPositiveButton("YES") { dialogInterface: DialogInterface, i: Int ->

                    dbHelper.delete_deletedata(m.idd)
                    var removedTaskFragment = RemovedTaskFragment()

                    var fm: FragmentManager = requireFragmentManager()
                    var ft: FragmentTransaction = fm.beginTransaction()
                    ft.replace(R.id.frmDisplayData,removedTaskFragment).commit()
                    Toast.makeText(requireActivity(), "Deleted", Toast.LENGTH_SHORT).show()
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