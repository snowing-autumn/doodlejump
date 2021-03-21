package com.example.jump

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.util.Log

class GameView @JvmOverloads constructor(context: Context?, attrs: AttributeSet?=null, defStyleAttr: Int = 0) : View(context,attrs, defStyleAttr)
    ,PositionChangeListener, Step.notifyDataChange {

    //运行状态
    private var isPause=false



    //左右倾斜
    private var isLeft=false
    //人物坐标
    private var positionX=0.0
    private var positionY=0.0
    //人物图像
    private var ladder = BitmapFactory.decodeResource(context?.resources,R.mipmap.missions_progress_bar_on_b)
    private var right = BitmapFactory.decodeResource(context?.resources,R.mipmap.likright_l)
    private var left =BitmapFactory.decodeResource(context?.resources,R.mipmap.likleft_r)
    //画笔
    private var doodlePaint=Paint()
    private var stepPaint=Paint()
    //梯子数据
    private var steps= Step.Steps.stepDatas
    //梯子高度间隔
    private var interval=300.0

    private val handler=MainActivity.handler
    private val accelerate=Accelerate(this,context!!)
    private val positionCalculator = PositionCalculator(context!!, handler)


    init {

        doodlePaint.isAntiAlias=true
        doodlePaint.textSize=60F
        stepPaint.isAntiAlias=true
        stepPaint.textSize=60F
        stepPaint.strokeWidth=30F
        stepPaint.color=Color.parseColor("#ad0015")
        Step.notifyList.add(this)
        Step.initSteps(context!!,interval)

    }

    fun pause(){
        isPause=true
    }

    fun start(){
        positionX = (context.resources.displayMetrics.widthPixels/2).toDouble()
        positionY = context.resources.displayMetrics.heightPixels.toDouble()-100
        positionCalculator.mPositionChangeListener=this
        Thread(accelerate).start()
        Thread(positionCalculator).start()
        isPause=false
    }

    override fun verticalPosition(positionY: Double) {
        this.positionY=positionY
        handler.post{invalidate()}
    }

    override fun getPositionY(): Double {
        return positionY
    }

    override fun horizontalMoveTendency(isLeft: Boolean, tendency: Double) {

        this.isLeft=isLeft
        this.positionX+=tendency
        if(positionX<0) positionX=context.resources.displayMetrics.widthPixels.toDouble()
        if(positionX>context.resources.displayMetrics.widthPixels)
            positionX=0.0
        handler.post{invalidate()}

    }

    override fun notifyDataChanges() {
        steps=Step.getInstance(context,interval)
        handler.post{invalidate()}
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        showDoodle(canvas)
        showStep(canvas)
    }

    //结束两线程
    fun stop(){
        accelerate.isRun=false
        positionCalculator.isRunning=false
    }

    private fun showDoodle(canvas: Canvas?){
        if(isLeft)
            canvas?.drawBitmap(left, (positionX-50).toFloat(),(positionY- 140).toFloat(),stepPaint)
        else
            canvas?.drawBitmap(right,(positionX-50).toFloat(),(positionY- 140).toFloat(),stepPaint)
        if(positionCalculator.isDropping)
            Step.isOnStep(positionX,positionY)
    }

    private fun showStep(canvas: Canvas?){
        for(step in steps){
            if(step.posY>0)
                canvas?.drawBitmap(ladder,step.posX.toFloat(),step.posY.toFloat(),stepPaint)
        }
    }



}