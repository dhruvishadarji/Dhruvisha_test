package com.dhruvisha.darji.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhruvisha.darji.R
import com.dhruvisha.darji.model.ImageResponce
import com.dhruvisha.darji.room.UserData
import kotlinx.android.synthetic.main.grid_row.view.*

class ImageAdapter(private val imageList: List<UserData>, private val context: Context) :
    RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = imageList[position].title
        Glide.with(context)
            .load(imageList[position].thumbnailUrl)
            .centerCrop()
            .into(holder.image)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.name
        val image: ImageView = itemView.image


    }
}