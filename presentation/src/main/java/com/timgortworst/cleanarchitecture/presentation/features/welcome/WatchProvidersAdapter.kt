package com.timgortworst.cleanarchitecture.presentation.features.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import com.timgortworst.cleanarchitecture.domain.model.movie.WatchProvider
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.WatchProviderListItemBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.BaseListAdapter

class WatchProvidersAdapter :
    BaseListAdapter<WatchProvidersAdapter.ViewItem, WatchProviderListItemBinding>(DiffUtilWatchProviders()) {
    var onCheckedListener: ((WatchProvider, Boolean) -> Unit)? = null

    override val itemViewType = R.layout.watch_provider_list_item

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> WatchProviderListItemBinding =
        WatchProviderListItemBinding::inflate

    override fun bind(binding: WatchProviderListItemBinding, item: ViewItem, position: Int) {
        binding.watchProvider.apply {
            setOnCheckedChangeListener(null)
            isChecked = item.isChecked
            text = item.watchProvider.providerName
            setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
                onCheckedListener?.invoke(item.watchProvider, isChecked)
            }
        }
    }

    data class ViewItem(
        val watchProvider: WatchProvider,
        var isChecked: Boolean,
    )
}
