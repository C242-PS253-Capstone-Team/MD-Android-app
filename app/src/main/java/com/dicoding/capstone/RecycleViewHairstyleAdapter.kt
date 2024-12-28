package com.dicoding.capstone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecycleViewHairstyleAdapter constructor(private val getActivity:Dashboard, private val hairstyleList: List<Hairstyle>):
    RecyclerView.Adapter<RecycleViewHairstyleAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
     val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_hairstyle_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvHairstyleTitle.text = hairstyleList[position].title
        holder.ivHairstyleImg.setImageResource(hairstyleList[position].image)

        holder.cardView.setOnClickListener{
            Toast.makeText(getActivity, hairstyleList[position].title, Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return hairstyleList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvHairstyleTitle : TextView = itemView.findViewById(R.id.tvHairstyleTitle)
        val ivHairstyleImg : ImageView = itemView.findViewById(R.id.ivHairstyleImg)
        val cardView : CardView = itemView.findViewById(R.id.cardView)
    }
}