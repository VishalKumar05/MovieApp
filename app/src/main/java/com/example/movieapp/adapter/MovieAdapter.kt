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
import com.example.movieapp.model.Collections
import kotlinx.android.synthetic.main.menu_content.view.*

class MovieAdapter(private val context: Context, private var dataList: List<Collections>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.menu_content,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(context,dataList.get(0).results,position)
        //holder.itemView.title.text = dataList.get(1).results[position].title
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private var poster = itemView.findViewById<ImageView>(R.id.poster)
        private var title = itemView.findViewById<TextView>(R.id.title)
        private var description = itemView.findViewById<TextView>(R.id.description)

        fun bindItems(context: Context, collections: MutableList<Collections.Result>, position: Int) {
            //title.text = collections.get(position).title
            //description.text = collections[0].results[position].overview
            //Glide.with(context).load(collections[0].results[position].posterPath).placeholder(R.drawable.placeholder).into(poster)
            Log.d("Size","Size: ${collections.size}")
            for (item in 0 until collections.size){
                title.text = collections[item].title
                description.text = collections[item].overview
                //Glide.with(context).load(item.posterPath).placeholder(R.drawable.placeholder).into(poster)
            }
        }

    }

}