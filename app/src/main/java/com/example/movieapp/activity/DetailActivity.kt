package com.example.movieapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.Details
import com.example.movieapp.viewModel.DetailsViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var mDetailsViewModel:DetailsViewModel
    private var itemId:String? = ""
    private var itemTitle:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        itemId = intent.getStringExtra("id")
        itemTitle = intent.getStringExtra("title")
        setup()
        observeData()
    }

    private fun setup() {
        mDetailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        itemId?.let { mDetailsViewModel.fetchDetailsPageData(it) }
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolBar.setNavigationOnClickListener { onBackPressed() }
        toolBar.title = itemTitle

        poster.setOnClickListener(this)
        playButton.setOnClickListener(this)
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

    override fun onClick(view: View?) {
        when (view?.id ) {
            R.id.poster -> {
                val intent = Intent(this, PlayerActivity::class.java)
                startActivity(intent)
            }
            R.id.playButton -> {
                val intent = Intent(this, PlayerActivity::class.java)
                startActivity(intent)
            }
        }
    }

}