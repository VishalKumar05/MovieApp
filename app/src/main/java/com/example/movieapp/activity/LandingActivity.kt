package com.example.movieapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.movieapp.R
import com.example.movieapp.fragment.LandingFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity(),TabLayout.OnTabSelectedListener {

    companion object{
        private val TAG = LandingActivity::class.java.simpleName
    }

    private val MENU_ITEMS = mapOf(
        "HOME" to "now_playing",
        "POPULAR" to "popular",
        "TOP RATED" to "top_rated",
        "UPCOMING" to "upcoming"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        setup()
    }

    private fun setup() {
        setSupportActionBar(toolBar)
        for (tabs in MENU_ITEMS){
            tabLayout.addTab(tabLayout.newTab().setText(tabs.key))
        }
        tabLayout.addOnTabSelectedListener(this)
        navigateToTab(MENU_ITEMS["HOME"])
    }

    override fun onTabReselected(p0: TabLayout.Tab?) { Log.d(TAG,"Tab Reselected") }

    override fun onTabUnselected(p0: TabLayout.Tab?) { Log.d(TAG,"Tab Unselected") }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        Log.d(TAG,"Tab Selected")
        val tabId = MENU_ITEMS[p0?.text]
        navigateToTab(tabId)
    }

    private fun navigateToTab(tabId: String?) {
        supportFragmentManager.beginTransaction().replace(R.id.pageContainer,LandingFragment(tabId!!)).commit()
    }

}
