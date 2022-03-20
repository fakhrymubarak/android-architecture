package com.fakhry.android_architechture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fakhry.android_architechture.databinding.ItemMovieBinding
import com.fakhry.android_architechture.network.response.MovieData

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val listMovie = ArrayList<MovieData>()

    var onItemClick: ((MovieData) -> Unit)? = null

    fun setData(data: List<MovieData>) {
        listMovie.clear()
        listMovie.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount() = listMovie.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listMovie[position]
        holder.bind(data)
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listMovie[layoutPosition])
            }
        }

        fun bind(data: MovieData) {
            with(binding) {
                tvTitle.text = data.title
                tvReleaseDate.text = data.releaseDate
                tvVoteAverage.text = data.voteAverage.toString()
                tvOverview.text = data.overview
                imageView.load(BuildConfig.POSTER_URL + data.posterPath)
            }
        }
    }
}
