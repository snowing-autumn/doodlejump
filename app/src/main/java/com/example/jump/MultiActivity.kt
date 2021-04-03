package com.example.jump

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText

class MultiActivity : AppCompatActivity() {
    private lateinit var singleButton:Button
    private lateinit var cooperateButton:Button
    private lateinit var historyButton:Button
    private lateinit var loginDialog:AlertDialog.Builder
    private lateinit var name:String
    private lateinit var myDialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi)

        singleButton=findViewById(R.id.button)
        cooperateButton=findViewById(R.id.button2)
        historyButton=findViewById(R.id.button3)

        singleButton.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            intent.putExtra("username",name)
            startActivity(intent)
        }
        historyButton.setOnClickListener {
            val intent=Intent(this,History::class.java)
            startActivity(intent)
        }


        loginDialog = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_my, null)
        val confirm:Button = dialogView.findViewById(R.id.confirm)
        dialogView.findViewById<TextInputEditText>(R.id.name_input).setText(
                getSharedPreferences("temp", MODE_PRIVATE).getString("name",""))
        confirm.setOnClickListener {
            name=dialogView.findViewById<TextInputEditText>(R.id.name_input).text.toString()
            myDialog.dismiss()
        }

        myDialog = loginDialog.setView(dialogView).show()


    }


    override fun onStop() {
        super.onStop()
        val editor = getSharedPreferences("temp", MODE_PRIVATE).edit()
        editor.putString("name",name).apply()
    }

}