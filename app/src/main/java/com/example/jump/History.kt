package com.example.jump

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class History : AppCompatActivity() {
    private lateinit var historyDataList:MutableList<HistoryData>
    private lateinit var db:SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history2)

        initHistoryData()

        val recyclerView=findViewById<RecyclerView>(R.id.historyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ScoreAdapter(historyDataList)

        val deleteButton:Button = findViewById(R.id.button_delete)
        val confirmButton:Button = findViewById(R.id.ok_button)

        confirmButton.setOnClickListener { finish() }
        deleteButton.setOnClickListener {
            db.delete("History",null,null)
            recyclerView.invalidate()
        }
    }

    private fun initHistoryData(){
        historyDataList= mutableListOf()
        db=HistoryHelper(this,"history.db",1).readableDatabase
        val cursor = db.query("History",null,null,null,null,null,null,null)
        if(cursor.moveToFirst())
            do{
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val score = cursor.getInt(cursor.getColumnIndex("score"))
                val mode = cursor.getInt(cursor.getColumnIndex("mode"))
                historyDataList.add(HistoryData(name, score, mode))
            }while (cursor.moveToNext())
        cursor.close()
        historyDataList.sort()
    }
}