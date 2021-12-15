package com.jeff.vrms.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jeff.vrms.databinding.ActivityVerificationBinding
import com.jeff.vrms.models.UserData
import com.google.firebase.database.ValueEventListener as ValueEventListener1

class Verification : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityVerificationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null){
            auth.currentUser?.let {
                binding.tvUserEmail.text = it.email
            }
        }

        binding.btnLoginOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, Login::class.java))
        }

        binding.btnContinue.setOnClickListener {
            if (binding.edId.text!!.isEmpty()){
                Toast.makeText(this@Verification,"enter id number!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                checkData()
            }

        }
    }

    private fun checkData() {
        val idNo = binding.edId.text.toString()
        val database = FirebaseDatabase.getInstance().reference
        val idRef: DatabaseReference = database.child("IdNos").child(idNo)
        val postListener = object : ValueEventListener1 {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()){
                    val intent = Intent(this@Verification, Registration::class.java)
                    intent.putExtra("IdNo", idNo)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(
                        this@Verification,
                        "not a registered citizen",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Toast.makeText(this@Verification,"error occurred",Toast.LENGTH_SHORT).show()
            }
        }
        idRef.addValueEventListener(postListener)
    }
}