package com.example.movieapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:imageSrc")
fun loadImage(imageView: ImageView, src:String?){
    src?.let { Glide.with(imageView.context).load(src).into(imageView) }
}
