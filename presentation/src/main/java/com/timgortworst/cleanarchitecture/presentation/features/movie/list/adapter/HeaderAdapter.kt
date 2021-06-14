package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListHeaderBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.BaseGridAdapter

class HeaderAdapter(
    text: String,
    private val spanSize: Int,
    private val itemPadding: Int,
) : BaseGridAdapter<String, MovieListHeaderBinding>(text) {

    override val itemViewType = R.layout.movie_list_header

    override fun getSpanSize() = spanSize

    override fun getItemOffset(parent: RecyclerView, view: View): Rect {
        return Rect().apply {
            left = itemPadding
            right = itemPadding
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MovieListHeaderBinding =
        MovieListHeaderBinding::inflate

    override fun bind(binding: MovieListHeaderBinding, item: String, position: Int) {
        binding.headerText.text = item
    }
}
