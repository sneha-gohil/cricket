package com.example.crickethub

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class formv2 : AppCompatActivity() {
    private lateinit var stime: EditText
    private lateinit var etime: EditText
    private lateinit var display: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formv2 )

        val date1: EditText = findViewById(R.id.date1)
        stime = findViewById(R.id.stime)
        etime = findViewById(R.id.etime)
        val nplayer: EditText = findViewById(R.id.nplayer)
        display = findViewById(R.id.display)
        val p2: Button = findViewById(R.id.p2)

        // Set time pickers for start_time and end_time
        stime.setOnClickListener {
            showTimePickerDialog(stime)
        }

        etime.setOnClickListener {
            showTimePickerDialog(etime)
        }

        // Calculate charges when end_time is entered
        etime.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (stime.text.isNotEmpty() && etime.text.isNotEmpty()) {
                    val totalHours = calculateTimeDifferenceInHours(stime.text.toString(), etime.text.toString())
                    if (totalHours > 0) {
                        val totalCharges = totalHours * 800
                        display.text = "Total Charges: Rs. $totalCharges"
                    } else {
                        Toast.makeText(this, "Invalid time range", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    display.text = ""
                }
            }
        }

        // Navigate to payment activity on button click
        p2.setOnClickListener {
            if (stime.text.isNotEmpty() && etime.text.isNotEmpty() && display.text.isNotEmpty()) {
                // Get total charges
                val totalCharges = display.text.toString().split("Rs. ")[1]

                // Venue name (can be dynamically selected or hardcoded)
                val venueName = "Box Arena 36"

                // Insert booking details into the database
                val dbHelper = dbhelper(this)
                val result = dbHelper.insertbook(
                    v_name = venueName,
                    date = date1.text.toString(),
                    startTime = stime.text.toString(),
                    endtime = etime.text.toString(),
                    charge = totalCharges,
                    no_of_player = nplayer.text.toString()
                )

                if (result > 0) {
                    // Successfully inserted, pass the book_id (result) to the next activity
                    val intent = Intent(this, pay::class.java).apply {
                        putExtra("book_id", result.toString()) // The auto-incremented book_id
                        putExtra("date", date1.text.toString())
                        putExtra("start_time", stime.text.toString())
                        putExtra("end_time", etime.text.toString())
                        putExtra("no_of_players", nplayer.text.toString())
                        putExtra("charges", totalCharges)
                    }
                    startActivity(intent)
                } else {
                    // Insertion failed
                    Toast.makeText(this, "Booking failed, please try again", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Please fill all fields and calculate charges", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showTimePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this,
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                editText.setText(formattedTime)
            },
            hour, minute, true)
        timePickerDialog.show()
    }

    private fun calculateTimeDifferenceInHours(startTime: String, endTime: String): Long {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val start = format.parse(startTime)!!
        val end = format.parse(endTime)!!
        val differenceInMillis = end.time - start.time
        return differenceInMillis / (1000 * 60 * 60) // Convert milliseconds to hours
    }
}
