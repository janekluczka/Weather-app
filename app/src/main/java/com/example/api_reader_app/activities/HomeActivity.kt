package com.example.api_reader_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.api_reader_app.R
import com.example.api_reader_app.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("HomeActivity", "onCreate called")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
    }
}