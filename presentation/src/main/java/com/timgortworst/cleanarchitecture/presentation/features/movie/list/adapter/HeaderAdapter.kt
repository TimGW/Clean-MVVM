package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListHeaderBinding

class HeaderAdapter(
    private var headerText: String
) : RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieListHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemViewType(position: Int): Int = R.layout.movie_list_header

    override fun getItemCount() = if (headerText.isBlank()) 0 else 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(headerText)
    }

    class ViewHolder(
        binding: MovieListHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val headerText: TextView = binding.headerText

        fun bind(text: String?) {
            headerText.text = text
        }
    }
}
