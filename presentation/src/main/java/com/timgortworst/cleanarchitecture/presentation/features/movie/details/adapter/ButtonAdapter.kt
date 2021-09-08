package com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.AdapterButtonBinding
import com.timgortworst.cleanarchitecture.presentation.extension.setMargins
import com.timgortworst.cleanarchitecture.presentation.model.Margins

class ButtonAdapter(
    private val buttonText: String = "",
    private val margins: Margins? = null
) : RecyclerView.Adapter<ButtonAdapter.ViewHolder>() {

    var onClickListener: ((View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding.button) {
            margins?.apply {
                setMargins(left, top, right, bottom)
            }
            text = buttonText
            setOnClickListener {
                onClickListener?.invoke(it)
            }
        }
    }

    override fun getItemViewType(position: Int) = R.layout.adapter_button

    override fun getItemCount() = 1

    class ViewHolder(val binding: AdapterButtonBinding) : RecyclerView.ViewHolder(binding.root)
}
