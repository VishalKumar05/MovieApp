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
import com.example.movieapp.model.Search

class SearchAdapter(private val context: Context,private val searchData:List<Search>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.search_item_renderer,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(context,searchData,position)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val title:TextView = itemView.findViewById(R.id.tile_title)
        private val poster = itemView.findViewById<ImageView>(R.id.preview_image)

        fun bindItems(context: Context,search: List<Search>, position: Int) {
            title.text = search[0].results.get(position).title
            val imageUrl = "https://image.tmdb.org/t/p/original/${search[0].results[position].posterPath}"
            Glide.with(context).load(imageUrl).placeholder(R.drawable.placeholder).into(poster)
        }

    }

    fun clearAdapter() {
        (searchData as MutableList).clear()
        notifyDataSetChanged()
    }

}