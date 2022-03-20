package com.fakhry.android_architechture.network

import com.fakhry.android_architechture.BuildConfig
import com.fakhry.android_architechture.network.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    /* Get Now Playing Movies  */
    @GET("movie/now_playing?")
    fun getMovPlayings(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): Call<MovieResponse>

    companion object {
        private const val API_KEY = BuildConfig.TMDB_API_KEY
    }
}