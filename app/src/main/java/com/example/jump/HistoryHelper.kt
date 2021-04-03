package com.example.jump

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistoryHelper(private val context: Context, name:String, version:Int):
        SQLiteOpenHelper(context, name, null, version) {
    private val createHistory = "create table History( id integer primary key autoincrement,"+
            "name text,"+"score integer,"+"mode integer)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createHistory)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            db.execSQL("drop table if exists History")
            onCreate(db)
        }
    }
}