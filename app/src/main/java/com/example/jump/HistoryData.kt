package com.example.jump

data class HistoryData(var name:String, var score:Int, var mode:Int) : Comparable<HistoryData>{
    override fun compareTo(other: HistoryData): Int {
        return if(this.score == other.score)
            this.name.compareTo(other.name)
        else
            other.score-this.score
    }
}