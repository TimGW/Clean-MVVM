package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.timgortworst.cleanarchitecture.data.BuildConfig
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemBinding

class MovieListAdapter(
    private var movieList: MutableList<Movie>,
    private val clickListener: (View, Movie) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun getItemCount() = movieList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
//        holder.title.text = movie.title

        val moviePostPath = BuildConfig.BASE_URL_IMAGES.plus(movie.posterPath)
        Picasso.get().load(moviePostPath)
            .placeholder(R.drawable.movie_placeholder)
            .centerCrop()
            .fit()
            .into(holder.moviePoster)

//        ViewCompat.setTransitionName(holder.moviePoster, movie.id.toString())

        holder.itemView.setOnClickListener {
            clickListener.invoke(it, movieList[position])
        }
    }

    inner class ViewHolder(binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        var title: TextView = binding.movieListItemTitle
        var moviePoster: ImageView = binding.moveListItemImage
    }

    fun addMoviesToList(movieList: MutableList<Movie>) {
        this.movieList.clear()
        this.movieList.addAll(movieList)
        notifyDataSetChanged()
    }
}
