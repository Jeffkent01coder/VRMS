package com.jeff.vrms.ui

import android.R.style.Theme_Holo_Light_Dialog_MinWidth
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.jeff.vrms.R
import com.jeff.vrms.databinding.ActivityRegistrationBinding
import com.jeff.vrms.models.County
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var database: DatabaseReference

    private val REQUEST_IMAGE_CAPTURE = 1

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker,year: Int,monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        val counties = arrayListOf<String>()
        val subcounties = arrayListOf<String>()

        val dbCounty = FirebaseDatabase.getInstance().getReference("Counties")
        dbCounty.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (countySnapshot in snapshot.children){
                        counties.add(countySnapshot.key.toString())
                        binding.countyDd.setOnFocusChangeListener { view, b ->
                            val selectedCounty = binding.countyDd.text.toString()
                            val dbSubCounty = dbCounty.child(selectedCounty)
                            dbSubCounty.addValueEventListener(object: ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()){
                                        for (i in snapshot.children){
                                            subcounties.add(i.key.toString())
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                            })
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }
        })


        binding.selectDate.setOnClickListener {
                DatePickerDialog(this,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

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

        val ward = arrayOf("gcdbsjd", "dsc")
        val location = arrayOf("DF","WE")
        val center = arrayOf("kithino", "kianamuthi")
        val countiesArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, counties)
        val subArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, subcounties)
        val wardArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, ward)
        val loactionArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, location)
        val centerArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, center)
        binding.countyDd.setAdapter(countiesArrayAdapter)
        binding.subcountyDd.clearListSelection()
        binding.subcountyDd.setAdapter(subArrayAdapter)
        binding.wardDd.setAdapter(wardArrayAdapter)
        binding.locationDd.setAdapter(loactionArrayAdapter)
        binding.votingCenterEt.setAdapter(centerArrayAdapter)

        binding.camPicker.setOnClickListener {
            imageGet()
        }

    }

    private fun imageGet() {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode,resultCode,data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            binding.camPicker.setImageBitmap(imageBitmap)
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM-dd-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.dateEt.text = sdf.format(cal.getTime())
    }
}