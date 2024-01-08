package com.example.wz_tracker.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.wz_tracker.R
import com.example.wz_tracker.databinding.FragmentUsernameBinding
import com.example.wz_tracker.viewModels.UsernameViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsernameFragment : Fragment() {

    private lateinit var binding: FragmentUsernameBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val viewModel: UsernameViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_username, container, false)

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        binding.apply {
            fragmentUsernameBtnSearch.setOnClickListener {
                saveUser()
            }

            fragmentUsernameBtnGoToCodWebsite.setOnClickListener {
                goToCodWebsite()
            }
        }

        return binding.root
    }

    private fun saveUser() {
        val activisionId = binding.fragmentUsernameEtInputActivisionId.text.toString()
        val isActivisionIdValid = viewModel.checkActivisionId(activisionId)
        if (isActivisionIdValid) {
            saveToSharedPreferences()
        } else {
            displayErrorMessage()
        }
    }

    private fun saveToSharedPreferences() {
        with(sharedPreferences.edit()) {
            putString("Username", viewModel.username)
            putString("Numbers", viewModel.numbers)
            apply()
        }

        Toast.makeText(context, "User saved", Toast.LENGTH_SHORT).show()
    }

    private fun displayErrorMessage() {
        binding.fragmentUsernameTextInputLayout.error = "Incorrect username"
    }

    private fun goToCodWebsite() {
        val codProfileSite = Uri.parse("https://profile.callofduty.com/cod/profile")
        val intent = Intent(Intent.ACTION_VIEW).setData(codProfileSite)
        startActivity(intent)
    }

}