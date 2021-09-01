package com.example.api_reader_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.api_reader_app.R
import com.example.api_reader_app.database.ForecastDatabase
import com.example.api_reader_app.databinding.FragmentDetailsBinding
import com.example.api_reader_app.viewmodels.DetailsViewModel
import com.example.api_reader_app.viewmodels.DetailsViewModelFactory
import com.example.api_reader_app.fragments.DetailsFragmentArgs as DetailsFragmentArgs1

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val args: DetailsFragmentArgs1 by navArgs()

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

        val application = requireNotNull(this.activity).application

        val dataSource = ForecastDatabase.getInstance(application).forecastDatabaseDao

        val viewModelFactory = DetailsViewModelFactory(args.id.toLong(), dataSource)

        val viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(DetailsViewModel::class.java)

        binding.detailsViewModel = viewModel
        binding.lifecycleOwner = this

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