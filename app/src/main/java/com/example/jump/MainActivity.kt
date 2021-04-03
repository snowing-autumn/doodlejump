package com.example.jump

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

interface onScoreChanged{
    fun scoreChange(score:Int)
}

class MainActivity : AppCompatActivity() ,onScoreChanged{

    private lateinit var mGameView:GameView
    private lateinit var mScoreView:TextView
    companion object handler: Handler()

    //历史成绩记录所需数据
    var score=0
    private var mode=1
    private var name=""
    private lateinit var db:SQLiteDatabase

    private lateinit var scoreAlert:AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        mGameView=findViewById(R.id.game_view)
        mScoreView=findViewById(R.id.score_view)
        mScoreView.text = ""+0
        mScoreView.textSize= 30F

        db=HistoryHelper(this,"history.db",1).writableDatabase
        name = intent.getStringExtra("username")!!

        scoreAlert=AlertDialog.Builder(this).apply {
            title = "游戏结束"
            setMessage("你的分数是$score")
            setCancelable(false)
            setPositiveButton("确认"){ _, _ -> gameOver() }
        }

        mGameView.start()

    }

    override fun scoreChange(score: Int) {
        this.score=score
        mScoreView.text = ""+score
        mScoreView.invalidate()
    }


    fun gameOver(){
        val value=ContentValues().apply {
            put("name",name)
            put("score",score)
            put("mode",mode)
        }
        val value2=ContentValues().apply { put("score",score) }
        if(db.query("History",arrayOf("name"),null,null,null,null,null)==null)
            db.insert("History",null,value)
        else
            db.update("History", value2, "name = ?", arrayOf(name))
        mGameView.stop()
        scoreAlert.show()
        finish()
    }

    override fun onStop() {
        super.onStop()
        score=0
    }
}