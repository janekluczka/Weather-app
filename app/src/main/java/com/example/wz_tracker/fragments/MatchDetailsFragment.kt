package com.example.wz_tracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.wz_tracker.R
import com.example.wz_tracker.database.Match
import com.example.wz_tracker.databinding.FragmentMatchDetailsBinding
import com.example.wz_tracker.util.BackgroundUtil
import com.example.wz_tracker.util.DateFormatterUtil
import com.example.wz_tracker.util.KdHelperUtil
import com.example.wz_tracker.util.ModeNameUtil
import com.example.wz_tracker.viewModels.MatchDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MatchDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMatchDetailsBinding

    private val args: MatchDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_match_details,
            container,
            false
        )

        val viewModel: MatchDetailsViewModel by viewModel { parametersOf(args.gameID) }

        viewModel.match.observe(viewLifecycleOwner) { match ->
            match?.let { setUpLayout(it) }
        }

        return binding.root
    }

    private fun setUpLayout(match: Match) {
        val placeBackgroundDrawable = BackgroundUtil.getMatchPlaceBackground(match.id)
        val kd = KdHelperUtil.calculateKd(match.kills, match.deaths)
        val kdBackgroundDrawable = BackgroundUtil.getMatchKdBackground(kd, match)

        binding.apply {
            fragmentMatchDetailsTvDate.text = DateFormatterUtil.getFormattedDate(match.date)
            fragmentMatchDetailsTvTime.text = DateFormatterUtil.getFormattedTime(match.date)
            fragmentMatchDetailsTvModeName.text = ModeNameUtil.getMatchMode(match.mode)
            fragmentMatchDetailsTvPlaceNumber.text = match.place.toString()
            fragmentMatchDetailsClPlace.background = getDrawable(placeBackgroundDrawable)
            fragmentMatchDetailsTvKillsAmount.text = match.kills.toString()
            fragmentMatchDetailsTvDeathsAmount.text = match.deaths.toString()
            fragmentMatchDetailsTvKdNumber.text = KdHelperUtil.formatKd(kd)
            fragmentMatchDetailsClKd.background = getDrawable(kdBackgroundDrawable)
            fragmentMatchDetailsTvDamageDoneAmount.text = match.damageDone.toString()
            fragmentMatchDetailsTvDamageTakenAmount.text = match.damageTaken.toString()
        }
    }

    private fun getDrawable(drawable: Int) = requireContext().getDrawable(drawable)

}