package com.example.api_reader_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.api_reader_app.databinding.FragmentListBinding
import kotlin.math.roundToInt

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.i("ListFragment", "onCreateView called")

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )

        binding.listViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = context?.let { ArrayAdapter(
            it,
            android.R.layout.simple_list_item_1,
            viewModel.next14Days
        )}

        binding.nextTwoWeeks.adapter = adapter

        binding.nextTwoWeeks.setOnItemClickListener { _, _, _, id ->
            Log.i("ListFragment", "Item nr $id clicked")

            val dayTemperature = viewModel.forecast.list[id.toInt()].temp.day.roundToInt()
            val nightTemperature = viewModel.forecast.list[id.toInt()].temp.night.roundToInt()

            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(
                "$dayTemperature°",
                "$nightTemperature°"
            )

            findNavController().navigate(action)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            Log.i("ListFragment", "onRefresh called")
            viewModel.callAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }

}