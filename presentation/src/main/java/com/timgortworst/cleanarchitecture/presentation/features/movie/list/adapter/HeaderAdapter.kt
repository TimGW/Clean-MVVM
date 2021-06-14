package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.graphics.Rect
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.base.BaseGridAdapter

class HeaderAdapter(
    text: String,
    private val spanSize: Int,
    private val itemPadding: Int,
) : BaseGridAdapter<String>(text) {

    override fun provideLayout() = R.layout.movie_list_header

    override fun getSpanSize() = spanSize

    override fun getItemOffset(parent: RecyclerView, view: View): Rect {
        return Rect().apply {
            left = itemPadding
            right = itemPadding
        }
    }

    override fun bind(itemView: View, item: String, position: Int) {
        itemView.findViewById<TextView>(R.id.header_text)?.text = item
    }
}
