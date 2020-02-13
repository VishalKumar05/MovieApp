package com.example.movieapp.viewModel

import com.example.movieapp.rest.ApiClient
import com.example.movieapp.rest.ApiInterface
import io.reactivex.disposables.CompositeDisposable

class LandingViewModel {

    companion object{
        private val TAG:String = LandingViewModel::class.java.simpleName
    }

    private var apiService = ApiClient.getClient().create(ApiInterface::class.java)
    private val disposable = CompositeDisposable()

}