package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class formv1 : AppCompatActivity() {
    private lateinit var dbhelper: dbhelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formv1)
        dbhelper = dbhelper(this)

        // Find views
        val v_name = intent.getStringExtra("v_name")
        val date1: EditText = findViewById(R.id.date1)
        val stime: EditText = findViewById(R.id.stime)
        val etime: EditText = findViewById(R.id.etime)
        val charge: EditText = findViewById(R.id.charge)
        val nplayer: EditText = findViewById(R.id.nplayer)
        val p1: Button = findViewById(R.id.p1)

        // Handle button click for saving booking
        p1.setOnClickListener {
            // Retrieve input values
            val vename = intent.getStringExtra("v_name")
            val v_id = vename?.let { it1 -> dbhelper.getVenueIdByName(it1) } ?: run {
                Toast.makeText(this, "Venue not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val date = date1.text.toString().trim()
            val startTime = stime.text.toString().trim()
            val endTime = etime.text.toString().trim()
            val chargeText = charge.text.toString().trim()
            val nplayersText = nplayer.text.toString().trim()

            // Validate inputs
            if (vename.isEmpty() || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty() ||
                chargeText.isEmpty() || nplayersText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convert charge and number of players to correct types
            val chargeValue: Double
            val numberOfPlayers: Int

            try {
                chargeValue = chargeText.toDouble()
                numberOfPlayers = nplayersText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid charge or number of players", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get v_id based on venue name
            if (v_name != null) {
                val v_id = dbhelper.getVenueIdByName(v_name) ?: run {
                    Toast.makeText(this, "Venue not found", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Insert booking into the database
            val result = dbhelper.insertbook(
                v_name = vename,
                date = date,
                startTime = startTime,
                endtime = endTime,
                charge = chargeText,
                no_of_player = nplayersText
            )

            // Handle the result of the database insertion
            if (result > 0) {
                Toast.makeText(this, "Booking saved successfully", Toast.LENGTH_SHORT).show()
                // start another activity (payment screen)
                val i1 = Intent(this, payment::class.java)
                startActivity(i1)
            } else {
                Toast.makeText(this, "Error saving booking", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
