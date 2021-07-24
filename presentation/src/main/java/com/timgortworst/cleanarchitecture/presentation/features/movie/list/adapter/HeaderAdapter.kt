package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListHeaderBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.BaseGridAdapter

class HeaderAdapter(
    text: String,
    private val spanSize: Int,
) : BaseGridAdapter<String, MovieListHeaderBinding>(text) {

    override val itemViewType = R.layout.movie_list_header

    override fun getSpanSize() = spanSize

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MovieListHeaderBinding =
        MovieListHeaderBinding::inflate

    override fun bind(binding: MovieListHeaderBinding, item: String, position: Int) {
        binding.headerText.text = item
    }
}
