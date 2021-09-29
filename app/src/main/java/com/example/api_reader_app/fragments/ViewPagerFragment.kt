package com.example.api_reader_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.api_reader_app.R
import com.example.api_reader_app.adapters.ViewPagerAdapter
import com.example.api_reader_app.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment() {

    private val args: ViewPagerFragmentArgs by navArgs()

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("ViewPagerFragment", "onCreateView called")

        val binding: FragmentViewPagerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_view_pager,
            container,
            false
        )

        viewPager = binding.fragmentViewPagerVp

        viewPager.adapter = ViewPagerAdapter(
            16, // change to list size later
            this
        )

        viewPager.setCurrentItem(args.id, false)

        return binding.root
    }



}