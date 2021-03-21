package com.example.jump

import android.content.Context
import android.util.Log

class Step {

    interface notifyDataChange{
        fun notifyDataChanges()
    }


    data class stepData(var posX:Double,var posY:Double){
    }
    companion object Steps{
        var positionCalculator= mutableListOf<PositionCalculator>()
        //待刷新列表
        var notifyList= mutableListOf<notifyDataChange>()
        //梯子数据
        var stepDatas = mutableListOf<Step.stepData>()
        //程序暂停/重启后重新创建梯子
        fun initSteps(context:Context,interval:Double){
            if(stepDatas.isNotEmpty())
                stepDatas.clear()
            val maxHeight=context.resources.displayMetrics.heightPixels

            for(i in 0..49){
                val y=maxHeight-i*interval
                val x=0.0+ (100..(context.resources.displayMetrics.widthPixels-400)).random()
                stepDatas.add(stepData(x,y))
            }
        }
        //获取梯子
        fun  getInstance(context:Context,interval:Double): MutableList<stepData> {
            if(stepDatas.isEmpty())
                initSteps(context,interval)
            return stepDatas
        }
        //跳跃上temp级台阶后更新梯子
        fun flashSteps(context:Context,interval:Double){
            val maxHeight=context.resources.displayMetrics.heightPixels.toDouble()
            //向下平移梯子的动画
            Thread {

                Log.e("进入子线程","进入子线程")
                var i=0
                while (i < 64) {
                    try {
                        Thread.sleep(10)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    for(step in stepDatas)
                        step.posY+=interval/64
                    i++
                    Log.e("梯子数量",""+ stepDatas.size)
                    notifyALL()
                }
                //删除溢出屏幕的梯子并增加新梯子
                val temp= stepDatas.filter { it.posY>maxHeight }.size
                stepDatas.removeIf{it.posY>maxHeight}
                Log.e("梯子数量2",""+ stepDatas.size)

                for(i in 0 until temp){
                    val y=maxHeight+(50-(temp-i))*interval
                    val x=0.0+ (100..(context.resources.displayMetrics.widthPixels-400)).random()
                    stepDatas.add(stepData(x,y))
                }
                Log.e("梯子数量3",""+ stepDatas.size)
                for(positionCal in positionCalculator)
                    positionCal.isStepChanging=false
                notifyALL()
            }.start()


        }
        //数据变更通知
        fun notifyALL(){
            for(i in notifyList)
                i.notifyDataChanges()
        }

        //检测是否成功跳跃
        fun isOnStep(posX:Double,posY:Double):Boolean{
            if(stepDatas.isNotEmpty())
                for(step in stepDatas)
                    if(step.posY-posY<40.0 && posY-step.posY<40.0
                            && posX-step.posX<200 && step.posX-posX<10 ){
                        for(i in positionCalculator)
                            i.collision()
                        return true
                    }
            return false
        }

    }



}