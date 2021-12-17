package com.jeff.vrms.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jeff.vrms.databinding.ActivityWebBinding

class Web : AppCompatActivity() {
    private lateinit var binding: ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWebBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}