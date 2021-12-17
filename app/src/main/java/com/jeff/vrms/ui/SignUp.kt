package com.jeff.vrms.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jeff.vrms.databinding.ActivitySignUpBinding
import com.jeff.vrms.models.User

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if (binding.edEmail.text!!.isEmpty()
                || binding.edPass.text!!.isEmpty()
                || binding.nameEt.text!!.isEmpty()
                || binding.edPhone.text!!.isEmpty()){
                Toast.makeText(this@SignUp,"all fields required",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                register()
            }
        }
    }

    private fun register(){
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.reference.child("Users")
        auth = FirebaseAuth.getInstance()

        val email = binding.edEmail.text.toString()
        val password = binding.edPass.text.toString()
        val username = binding.nameEt.text.toString()
        val phoneNo = binding.edPhone.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
//                    val currentUser = auth.currentUser
                    val user = User(username, email, phoneNo)
                    databaseReference.child(username).setValue(user)
                    startActivity(Intent(this@SignUp, Verification::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "registration failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }



    }

}
