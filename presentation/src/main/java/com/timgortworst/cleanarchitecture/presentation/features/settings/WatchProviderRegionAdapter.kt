package com.timgortworst.cleanarchitecture.presentation.features.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.timgortworst.cleanarchitecture.domain.model.movie.WatchProviderRegion
import com.timgortworst.cleanarchitecture.presentation.features.base.BaseArrayAdapter

class WatchProviderRegionAdapter(
    private val watchProviderRegions: List<WatchProviderRegion>,
    spinner: Spinner
) : BaseArrayAdapter<WatchProviderRegion>() {
    var selectedListener: ((WatchProviderRegion) -> Unit)? = null

    init {
        addAll(watchProviderRegions.toMutableList())
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedListener?.invoke(watchProviderRegions[position])
            }
        }
    }

    override fun getAdapterView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?,
        watchProviderRegion: WatchProviderRegion
    ) = inflateSpinnerAdapter(convertView, parent, watchProviderRegion)

    private fun inflateSpinnerAdapter(
        convertView: View?,
        parent: ViewGroup?,
        watchProviderRegion: WatchProviderRegion,
    ): View? {
        val inflater = LayoutInflater.from(parent?.context)
        val view: View?
        val vh: SpinnerViewHolder
        if (convertView == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
            vh = SpinnerViewHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as SpinnerViewHolder
        }
        vh.label.text = watchProviderRegion.nativeName
        return view
    }

    class SpinnerViewHolder(row: View?) {
        val label: TextView = row?.findViewById(android.R.id.text1) as TextView
    }
}
