package com.example.movieapp.activity

import android.content.Intent
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

class SearchActivity : AppCompatActivity(),View.OnClickListener,SearchAdapter.OnClickListener {

    private lateinit var mSearchViewModel:SearchViewModel
    private var delay:Long = AppConstants.SEARCH_DELAY
    private var timer:Timer? = null
    private var searchText:String? = null
    private lateinit var mAdapter:SearchAdapter
    private var filteredData:List<Search> = mutableListOf()

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
                if (searchText!!.length==1){
                    text_message.visibility = View.VISIBLE
                    text_message.text = getString(R.string.minimum_search_characters)
                }

                if (searchText!!.isNotEmpty() && searchText!!.length >= 2){
                    progressBar.visibility = View.VISIBLE
                    text_message.visibility = View.INVISIBLE
                    timer = Timer()
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            mSearchViewModel.fetchSearchPageData("320e458289dc0e7812b9b2236230c67e",searchText!!)
                        }
                    }, delay)
                }else if (recyclerView.adapter != null){
                    (recyclerView.adapter as SearchAdapter).clearAdapter()
                }
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        observeData()
        back_button.setOnClickListener(this@SearchActivity)
    }

    private fun observeData() {
        mSearchViewModel.getData().observe(this@SearchActivity, Observer<Search> { t ->
            if (t != null) {
                filteredData = listOf(t as Search)
                mAdapter = SearchAdapter(this, filteredData,this)
                recyclerView.adapter = mAdapter
                progressBar.visibility = View.GONE
            }else{
                text_message.text = getString(R.string.no_data)
                progressBar.visibility = View.GONE
                Toast.makeText(this,"Sorry, no data available",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back_button -> onBackPressed()
        }
    }

    override fun onItemClick(position: Int) {
        Log.d(TAG,"Position: $position")
        val itemId = filteredData[0].results[position].id
        val title:String = filteredData[0].results[position].title
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("id",itemId.toString())
        intent.putExtra("title",title)
        startActivity(intent)
    }
}