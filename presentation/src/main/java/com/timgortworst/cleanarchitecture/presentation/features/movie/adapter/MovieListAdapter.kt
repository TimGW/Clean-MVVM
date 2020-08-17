package com.timgortworst.cleanarchitecture.presentation.features.movie.adapter

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
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieListAdapter(
    private var movieList: MutableList<Movie>,
    private val clickListener: (Movie, ImageView, String) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun getItemCount() = movieList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.title.text = movie.title

        val moviePostPath = BuildConfig.BASE_URL_IMAGES.plus(movie.posterPath)
        Picasso.get().load(moviePostPath)
                .placeholder(R.drawable.movie_placeholder)
                .centerCrop()
                .fit()
                .into(holder.moviePoster)

        val transitionName =  movie.id.toString()
        ViewCompat.setTransitionName(holder.moviePoster, transitionName)

        holder.itemView.setOnClickListener {
            clickListener.invoke(movieList[position], holder.moviePoster, transitionName)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.movie_list_item_title
        var moviePoster: ImageView = itemView.move_list_item_image
    }

    fun addMoviesToList(movieList: MutableList<Movie>) {
        this.movieList.clear()
        this.movieList.addAll(movieList)
        notifyDataSetChanged()
    }
}
