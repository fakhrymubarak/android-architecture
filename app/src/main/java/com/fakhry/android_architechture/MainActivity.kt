package com.fakhry.android_architechture

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fakhry.android_architechture.databinding.ActivityMainBinding
import com.fakhry.android_architechture.network.ResponseStatus
import com.fakhry.android_architechture.network.response.MovieData

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val movieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListener()
        initViewModel()
    }

    private fun initView() {
        binding.rvMovie.adapter = movieAdapter
    }

    private fun initListener() {
        movieAdapter.onItemClick = { movie ->
            showToast("Clicked: ${movie.title}")
        }

        binding.svMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) viewModel.searchMovie(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) viewModel.searchMovie(query)
                return true
            }
        })
    }

    private fun initViewModel() {
        showLoading(true)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getAllMovies()
        viewModel.listMovie.observe(this) { response ->
            when (response.status) {
                ResponseStatus.SUCCESS -> {
                    showLoading(false)
                    setListMovie(response.body)
                }

                ResponseStatus.EMPTY -> {
                    showLoading(false)
                    showToast(response.message)
                }

                ResponseStatus.ERROR -> {
                    showLoading(false)
                    showToast(response.message)
                }
            }
        }
    }

    private fun setListMovie(data: List<MovieData>) {
        movieAdapter.setData(data)
        movieAdapter.notifyDataSetChanged()
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            this,
            message ?: "Failed show movies",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoading(state: Boolean) {
        val visibilityState = if (state) View.VISIBLE else View.INVISIBLE
        binding.apply {
            imgTransparent.visibility = visibilityState
            pbLoading.visibility = visibilityState
        }
    }
}