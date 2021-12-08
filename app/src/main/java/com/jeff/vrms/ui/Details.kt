package com.jeff.vrms.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jeff.vrms.databinding.ActivityDetailsBinding

class Details : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



    }
}