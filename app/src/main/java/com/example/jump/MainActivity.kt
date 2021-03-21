package com.example.jump

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    private lateinit var mGameView:GameView
    companion object handler:Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        mGameView=findViewById(R.id.game_view)
        mGameView.start()

    }

    override fun onStop() {
        super.onStop()
        mGameView.stop()
    }
}