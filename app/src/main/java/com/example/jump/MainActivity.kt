package com.example.jump

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView

interface onScoreChanged{
    fun scoreChange(score:Int)
}

class MainActivity : AppCompatActivity() ,onScoreChanged{

    private lateinit var mGameView:GameView
    private lateinit var mScoreView:TextView
    companion object handler:Handler()
    var score=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        mGameView=findViewById(R.id.game_view)
        mScoreView=findViewById(R.id.score_view)
        mScoreView.text = ""+0
        mScoreView.textSize= 30F

        mGameView.start()

    }

    override fun scoreChange(score: Int) {
        this.score=score
        mScoreView.text = ""+score
        mScoreView.invalidate()
    }

    override fun onStop() {
        super.onStop()
        score=0
        mGameView.stop()
    }
}