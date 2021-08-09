package com.example.api_reader_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.api_reader_app.databinding.FragmentDetailsBinding
import com.example.api_reader_app.DetailsFragmentArgs as DetailsFragmentArgs1

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val args: DetailsFragmentArgs1 by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.i("DetailsFragment", "onCreateView called")

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details,
            container,
            false
        )

        binding.dayTemperature.text = args.day
        binding.nightTemperature.text = args.night

        return binding.root
    }

}