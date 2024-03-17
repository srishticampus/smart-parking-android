package com.project.smartparking

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.smartparking.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var dataBase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        databaseSlotListner()

        binding.checkOutButton.setOnClickListener {
            if (binding.switchSlotOne.isClickable && binding.switchSlotTwo.isClickable) {

                if (binding.switchSlotOne.isChecked && binding.switchSlotTwo.isChecked) {
                    Toast.makeText(
                        applicationContext,
                        "Please choose any one slot",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (binding.switchSlotOne.isChecked) {
                    bookSlot("Slot1Status")
                } else if (binding.switchSlotTwo.isChecked) {
                    bookSlot("Slot2Status")
                }


            } else {
                if (binding.switchSlotOne.isClickable) {
                    if (binding.switchSlotOne.isChecked) {
                        bookSlot("Slot1Status")
                    }
                }
                if (binding.switchSlotTwo.isClickable) {
                    if (binding.switchSlotTwo.isChecked) {
                        bookSlot("Slot2Status")
                    }
                }
            }
        }


    }


    private fun bookSlot(selectedSlot: String) {
        var slot: String = "1"
        dataBase = FirebaseDatabase.getInstance().getReference(selectedSlot)
        dataBase.setValue(slot).addOnSuccessListener {
            Toast.makeText(applicationContext, "Booked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun freeSlotCount(count: String) {
        dataBase = FirebaseDatabase.getInstance().getReference("FreeSlots")
        dataBase.setValue(count).addOnSuccessListener {
            //Toast.makeText(applicationContext, "Booked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun databaseSlotListner() {
        dataBase = FirebaseDatabase.getInstance().getReference()
        val postListner = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val slotStatusOne = snapshot.child("Slot1Status").value.toString()
                val slotStatusTwo = snapshot.child("Slot2Status").value.toString()

                if (slotStatusOne.equals("1")) {
                    binding.switchSlotOne.isClickable = false
                    binding.switchSlotOne.isChecked = true
                    binding.switchSlotOne.text = "Booked"
                } else {
                    binding.switchSlotOne.isClickable = true
                    binding.switchSlotOne.isChecked = false
                    binding.switchSlotOne.text = ""

                }

                if (slotStatusTwo.equals("1")) {
                    binding.switchSlotTwo.isClickable = false
                    binding.switchSlotTwo.isChecked = true
                    binding.switchSlotTwo.text = "Booked"
                } else {
                    binding.switchSlotTwo.isClickable = true
                    binding.switchSlotTwo.isChecked = false
                    binding.switchSlotTwo.text = ""

                }

                if (slotStatusOne.equals("1") && slotStatusTwo.equals("1")) {
                    freeSlotCount("0")
                } else if (slotStatusOne.equals("1") && slotStatusTwo.equals("0")) {
                    freeSlotCount("1")
                } else if (slotStatusTwo.equals("1") && slotStatusOne.equals("0")) {
                    freeSlotCount("1")
                }else if (slotStatusTwo.equals("0") && slotStatusOne.equals("0")){
                    freeSlotCount("2")
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Failed to read sensor data", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        dataBase.addValueEventListener(postListner)
    }
}