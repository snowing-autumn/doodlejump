package com.example.jump

interface PositionChangeListener {
    fun verticalPosition(positionY: Double)
    fun horizontalMoveTendency(isLeft: Boolean,tendency: Double)
    fun getPositionY():Double
}