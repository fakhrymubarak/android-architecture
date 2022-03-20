package com.fakhry.android_architechture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fakhry.android_architechture.network.ApiConfig
import com.fakhry.android_architechture.network.ApiResponse
import com.fakhry.android_architechture.network.response.MovieData
import com.fakhry.android_architechture.network.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    fun getAllMovies(): LiveData<ApiResponse<List<MovieData>>> {
        val resultResponse = MutableLiveData<ApiResponse<List<MovieData>>>()

        ApiConfig.getApiService().getMovPlayings().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val data = responseBody.data

                        val apiResponse = if (data.isEmpty()) {
                            ApiResponse.empty(response.message(), emptyList())
                        } else {
                            ApiResponse.success(data)
                        }

                        resultResponse.postValue(apiResponse)
                    } else {
                        Log.e(TAG, "responseBodyNull: ${response.message()}")
                        resultResponse.postValue(ApiResponse.error(response.message(), emptyList()))
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    resultResponse.postValue(ApiResponse.error(response.message(), emptyList()))
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                resultResponse.postValue(ApiResponse.error(t.message.toString(), emptyList()))
            }
        })
        return resultResponse
    }

    companion object {
        private val TAG = MainViewModel::class.simpleName
    }
}