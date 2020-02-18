package com.example.movieapp.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.SearchAdapter
import com.example.movieapp.model.Search
import com.example.movieapp.utils.AppConstants
import com.example.movieapp.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var mSearchViewModel:SearchViewModel
    private var delay:Long = AppConstants.SEARCH_DELAY
    private var timer:Timer? = null
    private var searchText:String? = null
    private lateinit var mAdapter:SearchAdapter

    companion object{
        private val TAG = SearchActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setup()
    }

    private fun setup() {
        mSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        //mSearchViewModel.fetchSearchPageData("p")
        recyclerView.layoutManager = GridLayoutManager(this,2)

        //Code to listen for changed text during search
        search_editText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                if (timer != null)
                    timer?.cancel()

                if (search_editText.length() == 0) {
                    text_message.visibility = View.VISIBLE
                    text_message.text = getString(R.string.search_message)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                searchText = s.toString().toLowerCase()

                if (searchText!!.length in 1..2){
                    text_message.visibility = View.VISIBLE
                    text_message.text = getString(R.string.minimum_search_characters)
                }

                if (searchText!!.isNotEmpty() && searchText!!.length >= 3){
                    text_message.visibility = View.INVISIBLE
                    timer = Timer()
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            Log.d("Text","Text: $searchText")
                            mSearchViewModel.fetchSearchPageData("320e458289dc0e7812b9b2236230c67e",searchText!!)
                        }
                    }, delay)

                }else if (recyclerView.adapter != null){
                    (recyclerView.adapter as SearchAdapter).clearAdapter()
                }

                observeData()

                back_button.setOnClickListener(this@SearchActivity)
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}
        })
    }

    private fun observeData() {
        mSearchViewModel.getData().observe(this@SearchActivity, Observer<Search> { t ->
            if (t != null) {
                Log.d("Item","Item: ${t.totalPages}")
                mAdapter = SearchAdapter(this, listOf(t as Search))
                recyclerView.adapter = mAdapter


            }else{
                text_message.text = getString(R.string.no_data)
                Toast.makeText(this,"Sorry, no data available",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back_button -> onBackPressed()
        }
    }
}