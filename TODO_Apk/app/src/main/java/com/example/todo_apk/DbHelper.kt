package com.example.todo_apk


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DbHelper(context: Context) : SQLiteOpenHelper (context, DB_NAME, null, DB_VERSION) {

    companion object {
        var DB_NAME = "todo.db"

        var TABLE_NAME = "todo_task"
        var TABLE_NAME_UPDATE = "todo_task_update"
        var TABLE_NAME_COMPLETE = "todo_task_complete"
        var TABLE_NAME_DELETE = "todo_task_delete"


        var ID = "id"
        var TASK_ADD = "task_add"
        var TASK_DATE = "task_date"
        var TASK_CATEGORY = "task_category"

        var IDC = "idc"
        var TASK_ADDC = "task_addc"
        var TASK_DATEC = "task_datec"
        var TASK_CATEGORYC = "task_categoryc"

        var ID1 = "idu"
        var TASK_ADD1 = "updatetask_add"
        var TASK_DATE1 = "updatetask_date"
        var TASK_CATEGORY1 = "updatetask_category"

        var IDD = "idd"
        var TASK_ADDD = "deletetask_add"
        var TASK_DATED = "deletetask_date"
        var TASK_CATEGORYD = "deletetask_category"

        var DB_VERSION = 3
    }


    override fun onCreate(p0: SQLiteDatabase?) {
        var query =
            "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY," + TASK_ADD + " TEXT," + TASK_DATE + " TEXT," + TASK_CATEGORY + " TEXT" +")"

        var query_update =
            "CREATE TABLE " + TABLE_NAME_UPDATE + "(" + ID1 + " INTEGER PRIMARY KEY," + TASK_ADD1+ " TEXT," + TASK_DATE1 + " TEXT," + TASK_CATEGORY1 + " TEXT" +")"

        var query_complete_task =
            "CREATE TABLE " + TABLE_NAME_COMPLETE + "(" + IDC + " INTEGER PRIMARY KEY," + TASK_ADDC + " TEXT," + TASK_DATEC + " TEXT," + TASK_CATEGORYC + " TEXT" +")"

        var query_delete_task =
            "CREATE TABLE " + TABLE_NAME_DELETE + "(" + IDD + " INTEGER PRIMARY KEY," + TASK_ADDD + " TEXT," + TASK_DATED + " TEXT," + TASK_CATEGORYD + " TEXT" +")"

        p0!!.execSQL(query)
        p0!!.execSQL(query_update)
        p0!!.execSQL(query_complete_task)
        p0!!.execSQL(query_delete_task)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        var upquery = "DROP TABLE if exists " + TABLE_NAME

        var updatequery = "DROP TABLE if exists " + TABLE_NAME_UPDATE

        var completequery = "DROP TABLE if exists " + TABLE_NAME_COMPLETE

        var deletequery = "DROP TABLE if exists " + TABLE_NAME_DELETE

        p0!!.execSQL(upquery)
        p0!!.execSQL(updatequery)
        p0!!.execSQL(completequery)
        p0!!.execSQL(deletequery)

        onCreate(p0)
    }


    fun insertdata(m: Model): Long {
        var db = writableDatabase // you have to write something for data insertion

        var values = ContentValues()//add all data inside values

        values.put(TASK_ADD, m.task_name)
        values.put(TASK_DATE, m.task_date)
        values.put(TASK_CATEGORY, m.task_category)


        var id = db.insert(TABLE_NAME, ID, values)

        return id
    }

    fun insertUpdatedata(m: ModelUpdate): Long {
        var db = writableDatabase // you have to write something for data insertion

        var values = ContentValues()//add all data inside values

        values.put(TASK_ADD1, m.task_nameu)
        values.put(TASK_DATE1, m.task_dateu)
        values.put(TASK_CATEGORY1, m.task_categoryu)


        var id = db.insert(TABLE_NAME_UPDATE, ID1, values)

        return id
    }


    fun viewdata(): MutableList<Model> {

        var db = readableDatabase
        var list: ArrayList<Model> = ArrayList<Model>()
        var col = arrayOf(ID, TASK_ADD, TASK_DATE, TASK_CATEGORY)
        var cursor: Cursor = db.query(TABLE_NAME, col, null, null, null, null, null, null)
        //select * from tablename

        while (cursor.moveToNext()) {
            var id = cursor.getInt(0)
            var tasknm1 = cursor.getString(1)
            var taskdt1 = cursor.getString(2)
            var taskcategory1 = cursor.getString(3)


            var m = Model()
            m.id = id
            m.task_name = tasknm1
            m.task_date = taskdt1
            m.task_category = taskcategory1


            list.add(m)
        }
        return list
    }

    fun deletedata(id:Int)
    {
        var db = writableDatabase
        var deletequery = ID +" = " +id

        db.delete(TABLE_NAME,deletequery,null)

    }

    fun updatedeletedata(id:Int)
    {
        var db = writableDatabase
        var deletequery = ID1 +" = " +id

        db.delete(TABLE_NAME_UPDATE,deletequery,null)

    }
    fun completedeletedata(id:Int)
    {
        var db = writableDatabase
        var deletequery = IDC +" = " +id

        db.delete(TABLE_NAME_COMPLETE,deletequery,null)

    }
    fun delete_deletedata(id:Int)
    {
        var db = writableDatabase
        var deletequery = IDD +" = " +id

        db.delete(TABLE_NAME_DELETE,deletequery,null)

    }

    fun updatedata(m:ModelUpdate)
    {
        var db = writableDatabase
        var values = ContentValues()
        values.put(ID1,m.idu)
        values.put(TASK_ADD1,m.task_nameu)
        values.put(TASK_DATE1,m.task_dateu)
        values.put(TASK_CATEGORY1,m.task_categoryu)

        var updatequery = ID1+"="+m.idu


        db.update(TABLE_NAME_UPDATE,values,updatequery,null)

    }


    fun updateviewdata(): MutableList<ModelUpdate> {

        var db = readableDatabase
        var list: ArrayList<ModelUpdate> = ArrayList<ModelUpdate>()
        var col = arrayOf(ID1, TASK_ADD1, TASK_DATE1, TASK_CATEGORY1)
        var cursor: Cursor = db.query(TABLE_NAME_UPDATE, col, null, null, null, null, null, null)
        //select * from tablename

        while (cursor.moveToNext()) {
            var idu = cursor.getInt(0)
            var tasknm1 = cursor.getString(1)
            var taskdt1 = cursor.getString(2)
            var taskcategory1 = cursor.getString(3)


            var m = ModelUpdate()

            m.idu = idu
            m.task_nameu = tasknm1
            m.task_dateu = taskdt1
            m.task_categoryu = taskcategory1


            list.add(m)
        }
        return list
    }

    fun insertCompleteTaskdata(m: ModelCompletedTask): Long {
        var db = writableDatabase

        var values = ContentValues()

        values.put(TASK_ADDC, m.task_namec)
        values.put(TASK_DATEC, m.task_datec)
        values.put(TASK_CATEGORYC, m.task_categoryc)


        var id = db.insert(TABLE_NAME_COMPLETE, IDC, values)

        return id
    }


    fun completetask_viewdata(): MutableList<ModelCompletedTask> {

        var db = readableDatabase
        var list: ArrayList<ModelCompletedTask> = ArrayList<ModelCompletedTask>()
        var col = arrayOf(IDC, TASK_ADDC, TASK_DATEC, TASK_CATEGORYC)
        var cursor: Cursor = db.query(TABLE_NAME_COMPLETE, col, null, null, null, null, null, null)
        //select * from tablename

        while (cursor.moveToNext()) {
            var idc = cursor.getInt(0)
            var tasknm1 = cursor.getString(1)
            var taskdt1 = cursor.getString(2)
            var taskcategory1 = cursor.getString(3)


            var m = ModelCompletedTask()

            m.idc = idc
            m.task_namec = tasknm1
            m.task_datec = taskdt1
            m.task_categoryc = taskcategory1


            list.add(m)
        }
        return list
    }

    fun insertDeleteData(m: ModelDeletedTask): Long {
        var db = writableDatabase

        var values = ContentValues()

        values.put(TASK_ADDD, m.task_named)
        values.put(TASK_DATED, m.task_dated)
        values.put(TASK_CATEGORYD, m.task_categoryd)


        var id = db.insert(TABLE_NAME_DELETE, IDD, values)

        return id
    }

    fun deletetask_viewdata(): MutableList<ModelDeletedTask> {

        var db = readableDatabase
        var list: ArrayList<ModelDeletedTask> = ArrayList<ModelDeletedTask>()
        var col = arrayOf(IDD, TASK_ADDD, TASK_DATED, TASK_CATEGORYD)
        var cursor: Cursor = db.query(TABLE_NAME_DELETE, col, null, null, null, null, null, null)
        //select * from tablename

        while (cursor.moveToNext()) {
            var idc = cursor.getInt(0)
            var tasknm1 = cursor.getString(1)
            var taskdt1 = cursor.getString(2)
            var taskcategory1 = cursor.getString(3)


            var m = ModelDeletedTask()

            m.idd = idc
            m.task_named = tasknm1
            m.task_dated = taskdt1
            m.task_categoryd = taskcategory1


            list.add(m)
        }
        return list
    }
}



