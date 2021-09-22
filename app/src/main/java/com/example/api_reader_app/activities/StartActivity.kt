package com.example.api_reader_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.api_reader_app.R

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("StartActivity", "onCreate called")

        setContentView(R.layout.activity_start)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(
                    this,
                    MainActivity::class.java
                )
                startActivity(intent)
                finish()
            },
            3000
        )
    }

}