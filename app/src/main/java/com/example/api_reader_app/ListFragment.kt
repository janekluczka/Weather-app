package com.example.api_reader_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.api_reader_app.databinding.FragmentListBinding

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    private var list = arrayOf(
        "First",
        "Second",
        "Third",
        "Fourth",
        "Fifth",
        "Sixth",
        "Seventh",
        "Eighth",
        "Ninth",
        "Tenth",
        "Eleventh",
        "Twelfth",
        "Thirteenth",
        "Fourteenth",
        "Fifteenth"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )

        binding.lifecycleOwner = this

        val adapter = context?.let { ArrayAdapter(
            it,
            android.R.layout.simple_list_item_1,
            list
        )}

        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, _, id ->
            //Toast.makeText(context, "Item nr $id", Toast.LENGTH_SHORT).show()
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }


}