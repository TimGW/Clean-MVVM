package com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.AdapterTextBinding
import com.timgortworst.cleanarchitecture.presentation.extension.setMargins
import com.timgortworst.cleanarchitecture.presentation.model.Margins

class TextAdapter(
    private val text: String = "",
    private val margins: Margins? = null,
    @StyleRes private val style: Int? = null,
) : RecyclerView.Adapter<TextAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterTextBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        margins?.apply {
            view.root.setMargins(left, top, right, bottom)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        style?.let {
            TextViewCompat.setTextAppearance(holder.binding.root, style)
        }
        holder.binding.root.text = text
    }

    override fun getItemViewType(position: Int) = R.layout.adapter_text

    override fun getItemCount() = 1

    class ViewHolder(val binding: AdapterTextBinding) : RecyclerView.ViewHolder(binding.root)
}
