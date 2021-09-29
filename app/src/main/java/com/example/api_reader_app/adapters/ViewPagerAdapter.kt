package com.example.api_reader_app.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.api_reader_app.fragments.DetailsFragment

class ViewPagerAdapter(
    private val amount: Int,
    fragment: Fragment
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = amount

    override fun createFragment(position: Int): Fragment = DetailsFragment(position)

}