package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListHeaderBinding

class HeaderAdapter(
    private var headerText: String,
    private val spanSize: Int,
    private val itemPadding: Int,
) : BaseGridAdapter<HeaderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieListHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun provideItemViewType() = R.layout.movie_list_header

    override fun getItemCount() = if (headerText.isBlank()) 0 else 1

    override fun getSpanSize() = spanSize

    override fun getItemOffset(parent: RecyclerView, view: View): Rect {
        return Rect().apply {
            left = itemPadding
            right = itemPadding
        }
    }

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
