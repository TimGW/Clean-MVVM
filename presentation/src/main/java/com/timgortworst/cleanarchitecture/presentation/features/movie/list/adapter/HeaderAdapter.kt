package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.content.res.Resources
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListHeaderBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.decoration.AdapterDecoration

class HeaderAdapter(
    private val text: String = "",
) : RecyclerView.Adapter<HeaderAdapter.ViewHolder>(), AdapterDecoration {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieListHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(text)
    }

    override fun getItemViewType(position: Int) = R.layout.movie_list_header

    override fun getItemCount() = 1

    override fun getItemRect(
        resources: Resources,
        adapterPosition: Int,
        relativePosition: Int
    ): Rect {
        val spacing = resources.getDimension(R.dimen.default_padding).toInt()
        return Rect().apply { left = spacing; top = spacing; right = spacing }
    }

    inner class ViewHolder(
        private val binding: MovieListHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.headerText.text = text
        }
    }
}
