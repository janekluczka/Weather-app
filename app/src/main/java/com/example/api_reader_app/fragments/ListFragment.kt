package com.example.api_reader_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.api_reader_app.R
import com.example.api_reader_app.databinding.FragmentListBinding
import com.example.api_reader_app.viewmodels.ListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("ListFragment", "onCreateView called")

        val binding: FragmentListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )

        val viewModel by viewModel<ListViewModel>()

        binding.listViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                viewModel.daysList
            )
        }

        binding.fragmentListLvDaysList.adapter = adapter

        binding.fragmentListLvDaysList.setOnItemClickListener { _, _, _, id ->
            Log.i("ListFragment", "Item nr $id clicked")

            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(id.toInt())
            findNavController().navigate(action)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            Log.i("ListFragment", "onRefresh called")

            viewModel.getData()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.fragmentListSb.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    Log.i("ListFragment", "onProgressChanged called")

                    viewModel.numberOfDays = progress
                    viewModel.updateDaysList()
                    adapter?.notifyDataSetChanged()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )

        return binding.root
    }

}