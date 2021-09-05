package com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.AdapterStatisticsBinding
import com.timgortworst.cleanarchitecture.presentation.extension.format
import com.timgortworst.cleanarchitecture.presentation.extension.setMargins
import com.timgortworst.cleanarchitecture.presentation.model.Margins
import kotlin.math.roundToInt

class StatisticsAdapter(
    private val status: String,
    private val votes: Double,
    private val voteCount: Int,
    private val popularity: Double,
    private val margins: Margins? = null,
) : RecyclerView.Adapter<StatisticsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterStatisticsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            statisticStatus.text = status
            statisticVotes.text = votes.toString()
            statisticVoteCount.text = format(voteCount)
            statisticPopularity.text = popularity.roundToInt().toString()

            margins?.apply {
                root.setMargins(left, top, right, bottom)
            }
        }
    }

    override fun getItemViewType(position: Int) = R.layout.adapter_statistics

    override fun getItemCount() = 1

    class ViewHolder(val binding: AdapterStatisticsBinding) : RecyclerView.ViewHolder(binding.root)
}
