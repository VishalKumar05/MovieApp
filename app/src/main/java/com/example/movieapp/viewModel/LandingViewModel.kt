package com.example.movieapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.Collections
import com.example.movieapp.rest.ApiClient
import com.example.movieapp.rest.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LandingViewModel: ViewModel() {

    companion object{
        private val TAG:String = LandingViewModel::class.java.simpleName
    }

    private var apiService = ApiClient.getClient().create(ApiInterface::class.java)
    private val disposable = CompositeDisposable()
    private var data: MutableLiveData<Collections> = MutableLiveData()

    fun getData(): LiveData<Collections> {
        return data
    }

     fun fetchLandingPageData(itemId: String) {
         Log.d("Test","Item Id: $itemId")
        disposable.add(
            apiService.getLandingCollections(itemId)
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