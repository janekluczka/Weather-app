package com.example.wz_tracker.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wz_tracker.R
import com.example.wz_tracker.adapters.MatchesListAdapter
import com.example.wz_tracker.database.Match
import com.example.wz_tracker.databinding.FragmentMatchesListBinding
import com.example.wz_tracker.viewModels.MatchesListViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchesListFragment : Fragment() {

    private lateinit var binding: FragmentMatchesListBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val viewModel: MatchesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_matches_list,
            container,
            false
        )

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        viewModel.setUsernameAndNumbers(
            username = sharedPreferences.getString("Username", "") ?: "",
            numbers = sharedPreferences.getString("Numbers", "") ?: ""
        )

        if (viewModel.noUserSet()) {
            val action = MatchesListFragmentDirections.actionMatchesListFragmentToUsernameFragment()
            findNavController().navigate(action)
        }

        binding.fragmentMatchesListRvMatchesList.layoutManager = LinearLayoutManager(context)

        viewModel.matchesLiveData.observe(viewLifecycleOwner) { matches ->
            updateLayout(matches)
        }

        viewModel.fetchData()

        return binding.root
    }

    private fun updateLayout(matches: List<Match>?) {
        binding.fragmentMatchesListProgressIndicator.visibility = View.GONE
        if (matches != null) {
            setUpAdapter(matches)
        } else {
            showSomethingWentWrongSnackbar()
        }
    }

    private fun setUpAdapter(matches: List<Match>) {
        binding.fragmentMatchesListRvMatchesList.adapter =
            MatchesListAdapter(
                context = requireContext(),
                matches = matches.toTypedArray(),
                onClickListener = { gameID ->
                    val action = MatchesListFragmentDirections
                        .actionMatchesListFragmentToMatchDetailsFragment(gameID)
                    findNavController().navigate(action)
                }
            )
    }

    private fun showSomethingWentWrongSnackbar() = Snackbar
        .make(
            binding.fragmentMatchesListStatsCoordinatorLayout,
            requireContext().getText(R.string.something_went_wrong),
            Snackbar.LENGTH_INDEFINITE
        )
        .setAction(requireContext().getText(R.string.try_again)) { fetchData() }
        .show()

    private fun fetchData() {
        viewModel.fetchData()
        binding.fragmentMatchesListProgressIndicator.visibility = View.VISIBLE
    }

}