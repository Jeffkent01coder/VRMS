package com.jeff.vrms.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jeff.vrms.databinding.ActivityVerificationBinding

class Verification : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
            finish()
        }

    }
}