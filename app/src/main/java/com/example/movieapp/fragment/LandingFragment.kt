package com.example.movieapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movieapp.R
import kotlinx.android.synthetic.main.fragment_landing.view.*

class LandingFragment(private val tabId:String) : Fragment() {

    init {
        Log.d("Test","Tab Id: $tabId")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_landing,container,false)
        view.tv.text = "Fragment: $tabId"
        return view
    }

}