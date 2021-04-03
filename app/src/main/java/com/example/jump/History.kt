package com.example.jump

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history2)

        val dbHelper=HistoryHelper(this,"history.db",1)
        dbHelper.readableDatabase

    }
}