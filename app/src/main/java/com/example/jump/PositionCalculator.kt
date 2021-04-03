package com.example.jump

import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.Toast
import kotlin.math.sqrt

class PositionCalculator(private var mContext: Context, private val handler: Handler) :Thread(),Step.notifyDataChange{
    lateinit var mPositionChangeListener:PositionChangeListener
    //底端高度
    private var mHeight=0.0
    //跳跃状态
    var isDropping=false
    //进程状态
    var isRunning=true
    private var startTime=System.currentTimeMillis()

    private var interval=300.0
    //梯子列表
    private var steps= Step.getInstance(mContext,interval)
    //拐点高度
    private var heightWhileVyIsZero=400.0
    //剩余高度
    private var heightRemain=heightWhileVyIsZero
    //界面下移状态
    private var isDoodleChanging=false
    var isStepChanging=false


    init {
        resetHeight()
        Step.notifyList.add(this)
        Step.positionCalculator.add(this)
    }

    override fun notifyDataChanges() {
        steps=Step.getInstance(mContext,interval)
    }

    private fun resetHeight(){
        mHeight=mContext.resources.displayMetrics.heightPixels.toDouble()-100
    }

    fun collision(){
        isDropping=false
        heightRemain=heightWhileVyIsZero
    }

    fun flashDoodle(interval:Double){
        Thread {

            var i=0
            while (i < 64) {
                try {
                    sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                mHeight+=interval/64
                i++
                mPositionChangeListener.verticalPosition(mHeight)
            }
            isDoodleChanging=false
        }.start()
    }


    /**mHeight:当前纵坐标
     * deltaY：duration内移动的路程
     * heightWhileVyIsZero:一次单项跳跃最大位移
     * heightRemain:heightWhileVyIsZero剩余可移动位移，下降阶段记录已移动位移
    */
    override fun run() {
        super.run()

        while (isRunning){
            startTime = System.currentTimeMillis()
            try {
                sleep(10)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            var stopTime = System.currentTimeMillis()
            var duration=stopTime-startTime


            mHeight=mPositionChangeListener.getPositionY()
            val info=if(isDropping)"下降"
            else
                "上升"


            //下降阶段
            if(isDropping){
                //摔死了
                if (mHeight >= mContext.resources.displayMetrics.heightPixels.toDouble()) {
                    mPositionChangeListener.verticalPosition(mContext.resources.displayMetrics.heightPixels.toDouble()-1000 )
                    heightRemain=1.0
                    (mContext as MainActivity).gameOver()
                }else { //正常跳跃
                    if(heightRemain<=0)
                        heightRemain=1.0
                    Log.e("剩余高度",""+heightRemain)
                    if(heightRemain<=400)
                        heightRemain += sqrt(heightRemain)*duration/32
                    mHeight += sqrt(heightRemain) * duration/32
                    mPositionChangeListener.verticalPosition(mHeight)
                }
            }else{
                //Log.e("剩余高度",""+heightRemain)
                if(heightRemain>0){
                    if(mHeight<800&&!isDropping&&!isStepChanging) {
                        Log.e("开始刷新", "$isDropping  $isStepChanging")
                        isDoodleChanging=true
                        isStepChanging=true
                        Step.flashSteps(mContext, mContext.resources.displayMetrics.heightPixels.toDouble() - mHeight -400)
                        flashDoodle(mContext.resources.displayMetrics.heightPixels.toDouble() - mHeight -400)
                        sleep(10*64)
                    }
                    heightRemain -= sqrt(heightRemain)*duration/32
                    if(heightRemain<0) heightRemain=0.0
                    mHeight -= sqrt(heightRemain) * duration/32
                    mPositionChangeListener.verticalPosition(mHeight)
                }else{
                    heightRemain=0.0
                    isDropping=true
                }
                    
            }
        }
    }


}




