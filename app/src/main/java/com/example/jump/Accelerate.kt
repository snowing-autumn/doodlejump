package com.example.jump

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class Accelerate(var positionChangeListener:PositionChangeListener, private var context:Context):SensorEventListener,Runnable {
    private var sensor:Sensor
    private var sensorManager:SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    //水平运动倾向
    private var tendency=0.0
    var isRun=true

    init {
        //注册传感器
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_UI)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var x= -event!!.values[0]
        tendency = if(x<-0.2||x>0.2)
            (x*2).toDouble()
        else
            0.0

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun run() {
        while (isRun){
            try {
                Thread.sleep(5)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            when(tendency){
                0.0->continue
                else-> positionChangeListener.horizontalMoveTendency(tendency<0,tendency)
            }
        }
    }


}