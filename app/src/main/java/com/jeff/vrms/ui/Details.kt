package com.jeff.vrms.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jeff.vrms.databinding.ActivityDetailsBinding

class Details : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = intent
        val idNo = intent.getStringExtra("idNo").toString()
        val imageIv = intent.getStringExtra("image").toString()

        database = FirebaseDatabase.getInstance().getReference(idNo)

        database.child(idNo).get().addOnSuccessListener {
            if (it.exists()){
                val userName = it.child("name").value
                val userAge = it.child("age").value
                val gender = it.child("county").value
                val userDob = it.child("dob").value
                val userCounty = it.child("gender").value
                val userSubCounty = it.child("subCounty").value
                val userLocation = it.child("location").value
                val userWard = it.child("ward").value
                val userVotingCenter = it.child("votingCenter").value

                binding.nameEt.text = "Name: $userName"
                binding.useAgeEt.text = "Age: $userAge"
                binding.userGenderEt.text = "Gender: $gender"
                binding.dobEt.text = "DOB: $userDob"
                binding.userCountyEt.text = "County: $userCounty"
                binding.userSubCountyEt.text = "Sub-County: $userSubCounty"
                binding.userwardEt.text = "Ward: $userWard"
                binding.userlocationEt.text = "Location: $userLocation"
                binding.uservotingcenterEt.text = "Voting Center: $userVotingCenter"
                binding.idnoEt.text = idNo


            }
        }

    }
}