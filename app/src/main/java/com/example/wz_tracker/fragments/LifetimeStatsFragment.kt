package com.example.wz_tracker.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.wz_tracker.R
import com.example.wz_tracker.databinding.FragmentLifetimeStatsBinding
import com.example.wz_tracker.models.LifetimeStats
import com.example.wz_tracker.util.BackgroundUtil
import com.example.wz_tracker.util.KdHelperUtil
import com.example.wz_tracker.viewModels.LifetimeStatsViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LifetimeStatsFragment : Fragment() {

    private lateinit var binding: FragmentLifetimeStatsBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val viewModel: LifetimeStatsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflateDataBinding(inflater, container)

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        viewModel.setUsernameAndNumbers(
            username = sharedPreferences.getString("Username", "") ?: "",
            numbers = sharedPreferences.getString("Numbers", "") ?: ""
        )

        if (viewModel.noUserSet()) {
            val action = MatchesListFragmentDirections.actionMatchesListFragmentToUsernameFragment()
            findNavController().navigate(action)
        }

        viewModel.lifetimeStatsLiveData.observe(viewLifecycleOwner) { lifetimeStats ->
            updateLayout(lifetimeStats)
        }

        viewModel.fetchData()

        return binding.root
    }

    private fun inflateDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLifetimeStatsBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_lifetime_stats,
        container,
        false
    )

    private fun updateLayout(lifetimeStats: LifetimeStats?) {
        binding.fragmentLifetimeStatsProgressIndicator.visibility = View.GONE
        if (lifetimeStats != null) {
            setUpLayout(lifetimeStats)
        } else {
            showSomethingWentWrongSnackbar()
        }
    }

    private fun setUpLayout(lifetimeStats: LifetimeStats) {
        val wins = lifetimeStats.br_all.wins
        val winsBackgroundDrawable = BackgroundUtil.getLifetimeWinsBackground(wins)
        val kills = lifetimeStats.br_all.kills
        val deaths = lifetimeStats.br_all.deaths
        val kd = KdHelperUtil.calculateKd(kills, deaths)
        val kdBackgroundDrawable = BackgroundUtil.getLifetimeKdBackground(kd)

        binding.apply {
            fragmentLifetimeStatsSvStats.visibility = View.VISIBLE

            fragmentLifetimeStatsWinsAmount.text = wins.toString()
            fragmentLifetimeStatsClWins.background =
                getDrawableFromResources(winsBackgroundDrawable)

            fragmentLifetimeStatsTvKillsAmount.text = kills.toString()
            fragmentLifetimeStatsTvDeathsAmount.text = deaths.toString()

            fragmentLifetimeStatsTvKdNumber.text = KdHelperUtil.formatKd(kd)
            fragmentLifetimeStatsClKd.background =
                getDrawableFromResources(kdBackgroundDrawable)
        }
    }

    private fun showSomethingWentWrongSnackbar() =
        Snackbar.make(
            binding.fragmentLifetimeStatsCoordinatorLayout,
            requireContext().getText(R.string.something_went_wrong),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(requireContext().getText(R.string.try_again)) {
            viewModel.fetchData()
        }.show()

    private fun getDrawableFromResources(drawable: Int): Drawable? {
        return AppCompatResources.getDrawable(requireContext(), drawable)
    }

}