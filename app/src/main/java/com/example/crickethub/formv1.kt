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
        dbhelper=dbhelper(this)
        val vname:EditText=findViewById(R.id.vname)
        val date1:EditText=findViewById(R.id.date1)
        val stime:EditText=findViewById(R.id.stime)
        val etime:EditText=findViewById(R.id.etime)
        val charge:EditText=findViewById(R.id.charge)
        val nplayer:EditText=findViewById(R.id.nplayer)
        val p1:Button=findViewById(R.id.p1)
        p1.setOnClickListener {
            // Retrieve input values
            val venueName = vname.text.toString().trim()
            val startTime = stime.text.toString().trim()
            val endTime = etime.text.toString().trim()
            val charge = charge.text.toString().trim()
            val nplayers = nplayer.text.toString().trim()

            // Validate inputs
            if (venueName.isEmpty() || startTime.isEmpty() || endTime.isEmpty() ||
                charge.isEmpty() || nplayers.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val chargeValue: Double
            val numberOfPlayers: Int

            try {
                chargeValue = charge.toDouble()
                numberOfPlayers = nplayers.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid charge or number of players", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insert data into the database
            val result = dbhelper.insertbook(
                v_name = venueName,
                startTime = startTime,
                endtime = endTime,
                charge = charge,
            )

            if (result > 0) {
                Toast.makeText(this, "Booking saved successfully", Toast.LENGTH_SHORT).show()
                // Optionally, start another activity
                val i1 = Intent(this, payment::class.java)
                startActivity(i1)
            } else {
                Toast.makeText(this, "Error saving booking", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
