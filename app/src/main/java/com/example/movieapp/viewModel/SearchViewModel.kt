package com.example.movieapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.Search
import com.example.movieapp.rest.ApiClient
import com.example.movieapp.rest.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel:ViewModel() {

    companion object{
        private val TAG:String = SearchViewModel::class.java.simpleName
    }

    private var apiService = ApiClient.getClient().create(ApiInterface::class.java)
    private val disposable = CompositeDisposable()
    private var data: MutableLiveData<Search> = MutableLiveData()

    fun getData(): LiveData<Search> {
        return data
    }

    fun fetchSearchPageData(key:String,query: String){
        Log.d(TAG,"Query text: $query")
        disposable.add(
            apiService.getSearchCollections(key,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data.value = it
                },{
                    Log.d(TAG,it.localizedMessage)
                })
        )
    }

}