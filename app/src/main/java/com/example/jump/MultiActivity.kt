package com.example.jump

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MultiActivity : AppCompatActivity() {
    private lateinit var singleButton:Button
    private lateinit var cooperateButton:Button
    private lateinit var historyButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi)

        singleButton=findViewById(R.id.button)
        cooperateButton=findViewById(R.id.button2)
        historyButton=findViewById(R.id.button3)

        singleButton.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent) }
        historyButton.setOnClickListener {
            val intent=Intent(this,History::class.java)
            startActivity(intent)
        }

    }
}