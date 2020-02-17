package com.example.movieapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.Details
import com.example.movieapp.viewModel.DetailsViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var mDetailsViewModel:DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setup()
        observeData()
    }

    private fun setup() {
        mDetailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        mDetailsViewModel.fetchDetailsPageData("496243")
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolBar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun observeData() {
        mDetailsViewModel.getData().observe(this, Observer<Details> { it ->
            title_textView.text = it.title
            tagline_textView.text = it.tagline
            year_textView.text = it.releaseDate
            detail_textView.text = it.overview
            vote_textView.text = (it.voteAverage).toString()
            val imageUrl = "https://image.tmdb.org/t/p/original/${it.posterPath}"
            Glide.with(this).load(imageUrl).placeholder(R.drawable.placeholder).into(poster)
        })
    }

}