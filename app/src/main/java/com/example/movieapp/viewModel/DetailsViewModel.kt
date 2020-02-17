package com.example.movieapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.Collections
import com.example.movieapp.model.Details
import com.example.movieapp.rest.ApiClient
import com.example.movieapp.rest.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel:ViewModel() {

    companion object{
        private val TAG:String = DetailsViewModel::class.java.simpleName
    }

    private var apiService = ApiClient.getClient().create(ApiInterface::class.java)
    private val disposable = CompositeDisposable()
    private var data: MutableLiveData<Details> = MutableLiveData()

    fun getData(): LiveData<Details> {
        return data
    }

    fun fetchDetailsPageData(itemId:String){
        Log.d(TAG,"Item Id: $itemId")
        disposable.add(
            apiService.getDetailsCollections(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    data.value = it
                },{
                    Log.d(TAG,it.localizedMessage)
            })
        )
    }

}