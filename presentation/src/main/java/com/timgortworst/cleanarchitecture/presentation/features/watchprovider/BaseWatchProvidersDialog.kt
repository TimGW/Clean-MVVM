package com.timgortworst.cleanarchitecture.presentation.features.watchprovider

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentSettingsDialogWatchprovidersBinding
import com.timgortworst.cleanarchitecture.presentation.features.settings.WatchProvidersAdapter

open class BaseWatchProvidersDialog : DialogFragment() {
    var onCloseListener: (() -> Unit)? = null
    private var _binding: FragmentSettingsDialogWatchprovidersBinding? = null
    private val binding get() = _binding!!
    protected val watchProviderAdapter by lazy { WatchProvidersAdapter() }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentSettingsDialogWatchprovidersBinding
            .inflate(LayoutInflater.from(activity))

        binding.watchProviderRv.adapter = watchProviderAdapter

        return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.watch_providers))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                onCloseListener?.invoke()
                dismiss()
            }
            .setView(binding.root)
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
