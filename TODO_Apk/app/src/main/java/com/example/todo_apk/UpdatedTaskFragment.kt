package com.example.todo_apk

import android.annotation.SuppressLint
import android.content.DialogInterface
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

class UpdatedTaskFragment : Fragment() {

    lateinit var dbHelper: DbHelper
    lateinit var listView: ListView
    lateinit var layoutframe1:FrameLayout
    lateinit var listUpdate:MutableList<ModelUpdate>

    var mc = ModelCompletedTask()
    var model = Model()

    @SuppressLint("UseRequireInsteadOfGet", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_updated_task, container, false)
        dbHelper = DbHelper(requireActivity())

        listUpdate = ArrayList()
        listView = view.findViewById(R.id.listUpdate)
        layoutframe1 = view.findViewById(R.id.layoutframe1)


        var m = ModelUpdate()
            listUpdate = dbHelper.updateviewdata()

            var adapter = MyUpdateAdapter(requireActivity(), listUpdate)
            listView.adapter = adapter

            adapter.notifyDataSetChanged()
        listView.setOnCreateContextMenuListener(this)
        return view
    }
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?)
    {

        var m1: MenuItem = menu!!.add(0,1,0,"Complete Task")
        var m2: MenuItem = menu!!.add(0,2,0,"Delete Task")

        super.onCreateContextMenu(menu, v, menuInfo)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean
    {
        var acm: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        var pos = acm.position
        var m = listUpdate[pos]

        when(item.itemId)
        {

            1->
            {
                dbHelper.updatedeletedata(m.idu)

                var completedTaskFragment = CompletedTaskFragment()
                var fm: FragmentManager = requireFragmentManager()
                var ft: FragmentTransaction = fm.beginTransaction()
                val bundle = Bundle()
                bundle.putString("taskname", m.task_nameu)
                bundle.putString("taskdate", m.task_dateu)
                bundle.putString("taskcate", m.task_categoryu)

                completedTaskFragment.setArguments(bundle)
                ft.replace(R.id.frmDisplayData,completedTaskFragment).commit()

                Snackbar.make(layoutframe1, "Task Completed", Snackbar.LENGTH_SHORT).show()

            }

            2->
            {
                var alert = AlertDialog.Builder(requireActivity())
                alert.setTitle("Are you Sure you want to delete?")
                alert.setPositiveButton("YES") { dialogInterface: DialogInterface, i: Int ->



                    var updatedTaskFragment = UpdatedTaskFragment()

                    var fm: FragmentManager = requireFragmentManager()
                    var ft: FragmentTransaction = fm.beginTransaction()
                    ft.replace(R.id.frmDisplayData,updatedTaskFragment).commit()

                    dbHelper.updatedeletedata(m.idu)

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