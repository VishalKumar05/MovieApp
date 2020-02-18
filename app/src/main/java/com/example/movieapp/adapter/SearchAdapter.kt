package com.example.movieapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.Search

class SearchAdapter(private val context: Context,private val searchData:List<Search>, private val listener: SearchAdapter.OnClickListener): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.search_item_renderer,parent,false)
        return ViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return searchData[0].results.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(context,searchData[0].results,position)
    }

    class ViewHolder(itemView: View, listener: SearchAdapter.OnClickListener): RecyclerView.ViewHolder(itemView),View.OnClickListener{

        var onClickListener:OnClickListener = listener
        private val title:TextView = itemView.findViewById(R.id.tile_title)
        private val poster = itemView.findViewById<ImageView>(R.id.preview_image)

        fun bindItems(context: Context, item: MutableList<Search.Result>, position: Int) {
            title.text = item[position].title
            val imageUrl = "https://image.tmdb.org/t/p/original/${item[position].posterPath}"
            Glide.with(context).load(imageUrl).placeholder(R.drawable.placeholder).into(poster)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onClickListener.onItemClick(adapterPosition)
        }
    }

    interface OnClickListener{
        fun onItemClick(position:Int)
    }


    fun clearAdapter() {
        (searchData[0].results as MutableList).clear()
        notifyDataSetChanged()
    }

}