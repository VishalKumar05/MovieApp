package com.example.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.Collections

class MovieAdapter(private val context: Context, private var dataList: List<Collections>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.landing_content,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList[0].results.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(context, dataList[0].results,position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private var poster = itemView.findViewById<ImageView>(R.id.poster)
        private var title = itemView.findViewById<TextView>(R.id.title)
        private var description = itemView.findViewById<TextView>(R.id.description)

        fun bindItems(context: Context, collections: MutableList<Collections.Result>, position: Int) {
            title.text = collections[position].title
            description.text = collections[position].overview
            val imageUrl = "https://image.tmdb.org/t/p/original/${collections[position].posterPath}"
            Glide.with(context).load(imageUrl).placeholder(R.drawable.placeholder).into(poster)
        }
    }
}