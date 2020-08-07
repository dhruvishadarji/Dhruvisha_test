package com.dhruvisha.darji.ui.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.dhruvisha.darji.R
import com.dhruvisha.darji.room.UserData
import kotlinx.android.synthetic.main.grid_row.view.*
import java.net.URI
import java.net.URL

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
        val myURI = Uri.parse(imageList[position].thumbnailUrl)
        Glide.with(context)
            .load(myURI)
            .centerCrop()
            .into(holder.image)
        holder.layout.setOnClickListener {
            val intent= Intent(context,ImageActivity::class.java)
            intent.putExtra("Image_Url",imageList[position].url)
            context.startActivity(intent)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.name
        val image: ImageView = itemView.image
        val layout:ConstraintLayout = itemView.constraint_layout


    }
}