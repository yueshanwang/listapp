package com.example.listapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter internal constructor(private val data: List<MainActivity.ListData>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    //view holder for list data
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val leftView: TextView = view.findViewById(R.id.textView)
        val middleView: TextView = view.findViewById(R.id.textView2)
        val rightView: TextView = view.findViewById(R.id.textView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj = data[position]
        holder.leftView.text = obj.listID.toString()
        holder.middleView.text = obj.name
        holder.rightView.text = obj.id.toString()
    }
}