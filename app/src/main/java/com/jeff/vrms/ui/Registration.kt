package com.jeff.vrms.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.jeff.vrms.databinding.ActivityRegistrationBinding



class Registration : AppCompatActivity() {
    private lateinit var user : FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.btnLoginOut.setOnClickListener {
            logout()
        }

    }

    private fun logout() {

    }


}