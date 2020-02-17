package com.example.movieapp.rest

import com.example.movieapp.model.Collections
import com.example.movieapp.model.Details
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("3/movie/{id}?api_key=320e458289dc0e7812b9b2236230c67e&language=en-US&page=1")
    fun getLandingCollections(@Path("id")id:String):Observable<Collections>

    @GET("3/movie/{id}?api_key=320e458289dc0e7812b9b2236230c67e&language=en-US")
    fun getDetailsCollections(@Path("id")id: String):Observable<Details>

}