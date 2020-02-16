package com.example.movieapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.model.Collections
import com.example.movieapp.viewModel.LandingViewModel
import kotlinx.android.synthetic.main.fragment_landing.*


class LandingFragment(private val tabId:String) : Fragment() {

    private var binding: ViewDataBinding? = null
    private lateinit var mLandingViewModel: LandingViewModel
    private lateinit var mAdapter:MovieAdapter
    private var movieData:List<Collections> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.example.movieapp.R.layout.fragment_landing,container,false)
        //binding = activity?.let { DataBindingUtil.setContentView(it,R.layout.fragment_landing) }
        mLandingViewModel = ViewModelProviders.of(requireActivity()).get(LandingViewModel::class.java)
        mLandingViewModel.fetchLandingPageData(tabId)
        observeData()
        return view
    }

     private fun observeData() {
         mLandingViewModel.getData().observe(this, Observer<Collections> { it ->
             progressBar.visibility = View.VISIBLE
             movieData = listOf(it as Collections)
             val mAdapter = activity?.let { it -> MovieAdapter(it,movieData) }
             recyclerView.apply {
                 layoutManager = LinearLayoutManager(activity)
                 adapter = mAdapter
             }
             progressBar.visibility = View.GONE
         })
     }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}

