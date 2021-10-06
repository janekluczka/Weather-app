package com.example.api_reader_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.api_reader_app.R
import com.example.api_reader_app.databinding.FragmentDetailsBinding
import com.example.api_reader_app.viewModels.DetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment(
    private val position: Int
) : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("DetailsFragment", "onCreateView called")

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details,
            container,
            false
        )

        val viewModel: DetailsViewModel by viewModel{ parametersOf(position.toLong()) }

        binding.detailsViewModel = viewModel

        viewModel.forecast.observe(viewLifecycleOwner, {
            viewModel.setNewTemperatures()
        })

        viewModel.dayTemperature.observe(viewLifecycleOwner, { newTemperature ->
            binding.fragmentDetailsTxtDayTemp.text = newTemperature
        })

        viewModel.nightTemperature.observe(viewLifecycleOwner, { newTemperature ->
            binding.fragmentDetailsTxtNightTemp.text = newTemperature
        })

        return binding.root
    }

}