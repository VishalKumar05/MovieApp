package com.example.movieapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Collect {


    @SerializedName("results")
    @Expose
    private var results: List<Result>? = null
    @SerializedName("page")
    @Expose
    private var page: Int? = null
    @SerializedName("total_results")
    @Expose
    private var totalResults: Int? = null
    @SerializedName("dates")
    @Expose
    private var dates: Dates? = null
    @SerializedName("total_pages")
    @Expose
    private var totalPages: Int? = null

    fun getResults(): List<Result>? {
        return results
    }

    fun setResults(results: List<Result>) {
        this.results = results
    }

    fun getPage(): Int? {
        return page
    }

    fun setPage(page: Int?) {
        this.page = page
    }

    fun getTotalResults(): Int? {
        return totalResults
    }

    fun setTotalResults(totalResults: Int?) {
        this.totalResults = totalResults
    }

    fun getDates(): Dates? {
        return dates
    }

    fun setDates(dates: Dates) {
        this.dates = dates
    }

    fun getTotalPages(): Int? {
        return totalPages
    }

    fun setTotalPages(totalPages: Int?) {
        this.totalPages = totalPages
    }


    inner class Dates {

        @SerializedName("maximum")
        @Expose
        var maximum: String? = null
        @SerializedName("minimum")
        @Expose
        var minimum: String? = null

    }


    inner class Result {

        @SerializedName("popularity")
        @Expose
        var popularity: Double? = null
        @SerializedName("vote_count")
        @Expose
        var voteCount: Int? = null
        @SerializedName("video")
        @Expose
        var video: Boolean? = null
        @SerializedName("poster_path")
        @Expose
        var posterPath: String? = null
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("adult")
        @Expose
        var adult: Boolean? = null
        @SerializedName("backdrop_path")
        @Expose
        var backdropPath: String? = null
        @SerializedName("original_language")
        @Expose
        var originalLanguage: String? = null
        @SerializedName("original_title")
        @Expose
        var originalTitle: String? = null
        @SerializedName("genre_ids")
        @Expose
        var genreIds: List<Int>? = null
        @SerializedName("title")
        @Expose
        var title: String? = null
        @SerializedName("vote_average")
        @Expose
        var voteAverage: Double? = null
        @SerializedName("overview")
        @Expose
        var overview: String? = null
        @SerializedName("release_date")
        @Expose
        var releaseDate: String? = null

    }


}