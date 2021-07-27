package com.timgortworst.cleanarchitecture.presentation.features.welcome

import androidx.recyclerview.widget.DiffUtil

class DiffUtilWatchProviders : DiffUtil.ItemCallback<WatchProvidersAdapter.ViewItem>() {
    override fun areItemsTheSame(
        oldItem: WatchProvidersAdapter.ViewItem,
        newItem: WatchProvidersAdapter.ViewItem
    ) = oldItem.watchProvider.providerId == newItem.watchProvider.providerId

    override fun areContentsTheSame(
        oldItem: WatchProvidersAdapter.ViewItem,
        newItem: WatchProvidersAdapter.ViewItem
    ) = oldItem == newItem
}