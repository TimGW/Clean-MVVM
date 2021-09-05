package com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.AdapterGenresBinding
import com.timgortworst.cleanarchitecture.presentation.extension.setMargins
import com.timgortworst.cleanarchitecture.presentation.model.Margins

class GenresAdapter(
    private val genres: List<MovieDetails.Genre>,
    private val margins: Margins? = null,
) : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterGenresBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            margins?.apply {
                root.setMargins(left, top, right, bottom)
            }

            genres.forEach {
                root.addView(buildChip(holder.itemView.context, it))
            }
        }
    }

    private fun buildChip(
        context: Context,
        genre: MovieDetails.Genre
    ) = Chip(context).apply {
        id = ViewCompat.generateViewId()
        text = genre.name
    }

    override fun getItemViewType(position: Int) = R.layout.adapter_genres

    override fun getItemCount() = 1

    class ViewHolder(val binding: AdapterGenresBinding) : RecyclerView.ViewHolder(binding.root)
}
