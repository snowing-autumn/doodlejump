package com.example.jump

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ScoreAdapter(val historyDataList:List<HistoryData>) :RecyclerView.Adapter<ScoreAdapter.ViewHolder>(){
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val holderName=view.findViewById<TextView>(R.id.holder_name)
        val holderScore=view.findViewById<TextView>(R.id.holder_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.score_holder,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyData = historyDataList[position]
        holder.holderName.text = historyData.name
        holder.holderScore.text = ""+historyData.score
    }

    override fun getItemCount()=historyDataList.size
}