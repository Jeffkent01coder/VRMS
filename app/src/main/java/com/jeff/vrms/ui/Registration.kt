package com.jeff.vrms.ui

import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import com.google.firebase.database.*
import com.jeff.vrms.R
import com.jeff.vrms.databinding.ActivityRegistrationBinding
import com.jeff.vrms.models.UserRegData
import java.text.SimpleDateFormat
import java.util.*


class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var database: DatabaseReference
    private lateinit var imageSaved: Bitmap

    private val REQUEST_IMAGE_CAPTURE = 1

    var cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvWeb.setOnClickListener {
            startActivity(Intent(this, Web::class.java))
        }

        val intent = intent
        val idNo = intent.getStringExtra("IdNo").toString()

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker,year: Int,monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

//        val county: St
//        val counties = mutableListOf<String>()
//        val subcounties = mutableListOf<String>()
//        val wards = mutableListOf<String>()
//        val locations = mutableListOf<String>()
//        val centers = mutableListOf<String>()

        //drop down data
//        val dbCounty = FirebaseDatabase.getInstance().getReference("Counties")
//        dbCounty.addValueEventListener(object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()){
//                    for (countySnapshot in snapshot.children){
//                        counties.add(countySnapshot.key.toString())
//                        binding.countyDd.setOnFocusChangeListener { _,_ ->
//                            val selectedCounty = binding.countyDd.text.toString()
//                            val dbSubCounty = dbCounty.child(selectedCounty)
//                            dbSubCounty.addValueEventListener(object: ValueEventListener{
//                                override fun onDataChange(snapshot: DataSnapshot) {
//                                    if (snapshot.exists()){
//                                        for (i in snapshot.children){
//                                            subcounties.add(i.key.toString())
//                                            binding.subcountyDd.setOnFocusChangeListener { _, _ ->
//                                                binding.subcountyDd.clearListSelection()
//                                                subcounties.clear()
//                                                val selectedSubCounty = binding.subcountyDd.text.toString()
//                                                val dbWard = dbSubCounty.child(selectedSubCounty)
//                                                dbWard.addValueEventListener(object : ValueEventListener{
//                                                    override fun onDataChange(snapshot: DataSnapshot) {
//                                                        if (snapshot.exists()){
//                                                            for (ward in snapshot.children){
//                                                                wards.add(ward.key.toString())
//                                                                binding.wardDd.setOnFocusChangeListener { _, _ ->
//                                                                    binding.wardDd.clearListSelection()
//                                                                    wards.clear()
//                                                                    val selectedWard = binding.wardDd.text.toString()
//                                                                    val dblocation = dbWard.child(selectedWard)
//                                                                    dblocation.addValueEventListener(object: ValueEventListener{
//                                                                        override fun onDataChange(
//                                                                            snapshot: DataSnapshot
//                                                                        ) {
//                                                                            if (snapshot.exists()){
//                                                                                for (loc in snapshot.children){
//                                                                                    locations.add(loc.key.toString())
//                                                                                    binding.locationDd.setOnFocusChangeListener { _, _ ->
//                                                                                        binding.locationDd.clearListSelection()
//                                                                                        locations.clear()
//                                                                                        val selectedLocation = binding.locationDd.text.toString()
//                                                                                        val dbcenter = dblocation.child(selectedLocation)
//                                                                                        dbcenter.addValueEventListener(object: ValueEventListener{
//                                                                                            override fun onDataChange(snapshot: DataSnapshot) {
//                                                                                                if (snapshot.exists()){
//                                                                                                    for (cent in snapshot.children){
//                                                                                                        centers.add(cent.value.toString())
//                                                                                                        binding.votingCenterDd.clearListSelection()
////                                                                                                        centers.clear()
//                                                                                                    }
//                                                                                                }
//                                                                                            }
//
//                                                                                            override fun onCancelled(
//                                                                                                error: DatabaseError
//                                                                                            ) {
//                                                                                                TODO(
//                                                                                                    "Not yet implemented"
//                                                                                                )
//                                                                                            }
//
//                                                                                        })
//                                                                                    }
//                                                                                }
//                                                                            }
//                                                                        }
//
//                                                                        override fun onCancelled(
//                                                                            error: DatabaseError
//                                                                        ) {
//                                                                            TODO("Not yet implemented")
//                                                                        }
//
//                                                                    })
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//
//                                                    override fun onCancelled(error: DatabaseError) {
//                                                        TODO("Not yet implemented")
//                                                    }
//
//                                                })
//                                            }
//                                        }
//                                    }
//                                }
//
//                                override fun onCancelled(error: DatabaseError) {
//                                    TODO("Not yet implemented")
//                                }
//
//                            })
//                        }
//                    }
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "loadPost:onCancelled", error.toException())
//            }
//        })



        binding.selectDate.setOnClickListener {
                DatePickerDialog(this,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        database = FirebaseDatabase.getInstance().getReference("IdNos")

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



//        val countiesArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, counties)
//        val subArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, subcounties)
//        val wardArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, wards)
//        val locationArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, locations)
//        val centerArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, centers)
//        binding.countyDd.setAdapter(countiesArrayAdapter)


//        binding.subcountyDd.setAdapter(subArrayAdapter)
//        binding.wardDd.setAdapter(wardArrayAdapter)
//        binding.locationDd.setAdapter(locationArrayAdapter)
//        binding.votingCenterDd.setAdapter(centerArrayAdapter)

        binding.camPicker.setOnClickListener {
            imageGet()
        }
        binding.submitBtn.setOnClickListener {
            submitForm(idNo)
        }

    }

    fun submitForm(idNo: String) {

        val gender = when(binding.radioGroup.checkedRadioButtonId){
            R.id.genderMale -> "male"
            else -> "female"
        }
        val databaseReg = FirebaseDatabase.getInstance().getReference("IdNos")

        val data = UserRegData(
            1,
            binding.NameEd!!.text!!.toString(),
            binding.dateEt.text.toString(),
            binding.ageEt.text.toString(),
            gender,
            binding.countyDd.text.toString(),
            binding.subcountyDd.text.toString(),
            binding.wardDd.text.toString(),
            binding.votingCenterDd.text.toString())

        databaseReg.child(idNo).setValue(data).addOnSuccessListener {
            Toast.makeText(this,"added successfully",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Details::class.java)
            intent.putExtra("idNo", idNo)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
                Toast.makeText(this,"not saved",Toast.LENGTH_SHORT).show()
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
            imageSaved = data!!.extras!!.get("data") as Bitmap
            binding.camPicker.setImageBitmap(imageSaved)
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM-dd-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.dateEt.text = sdf.format(cal.getTime())
    }
}