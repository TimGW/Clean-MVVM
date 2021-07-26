package com.timgortworst.cleanarchitecture.presentation.features.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.timgortworst.cleanarchitecture.data.database.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private val viewModel by viewModels<WelcomeViewModel>()

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.regions.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> { }
                Resource.Loading -> { }
                is Resource.Success -> {
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        it.data.map { it.englishName }
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.regionSpinner.adapter = adapter
                }
            }

        }

        binding.buttonDone.setOnClickListener {
            requireActivity().finish()
            findNavController().navigate(R.id.MovieActivity)
            sharedPrefs.setOnboardingDone(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
