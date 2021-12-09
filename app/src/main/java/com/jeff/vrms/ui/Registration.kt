package com.jeff.vrms.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jeff.vrms.databinding.ActivityRegistrationBinding


class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("IdNos")

        val intent = intent
        val idNo = intent.getStringExtra("IdNo").toString()

        database.child(idNo).get().addOnSuccessListener {
            if (it.exists()){
                val firstname = it.child("firstname").value
                val lastname = it.child("lastname").value
                binding.NameEd.text = "$firstname $lastname"
                binding.idEd.text = idNo
            }else{
                Toast.makeText(this@Registration,"user doesn't exist!",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@Registration, Verification::class.java))
                finish()
            }
        }


    }
}