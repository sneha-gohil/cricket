package com.example.crickethub

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class form1 : AppCompatActivity() {
    private lateinit var dbhelper: dbhelper
    private lateinit var date1:EditText
    private lateinit var stime:EditText
    private lateinit var etime:EditText
    private lateinit var disply:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form1)

        val date1: EditText = findViewById(R.id.date1)
        val stime: EditText = findViewById(R.id.stime)
        val etime: EditText = findViewById(R.id.etime)
        val nplayer: EditText = findViewById(R.id.nplayer)
        val disply: TextView = findViewById(R.id.disply)
        val p1: Button = findViewById(R.id.p1)


        date1.setOnClickListener {
            showDatePickerDialog()
        }

        stime.setOnClickListener {
            showTimePickerDialog { selectedTime ->
                stime.text = selectedTime as Editable? // Use text property
            }
        }

        etime.setOnClickListener {
            showTimePickerDialog { selectedTime ->
                etime.text = selectedTime as Editable? // Use text property
            }
        }

        p1.setOnClickListener {
            val intent=Intent(this,payment::class.java)
            startActivity(intent)
        }

    }

        private fun showDatePickerDialog() {
            // Create a Calendar instance to get the current date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Create a DatePickerDialog
            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                // Set the selected date in the EditText
                date1.setText(String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear))
            }, year, month, day)

            // Show the DatePickerDialog
            datePickerDialog.show()
        }

    private fun showTimePickerDialog(param: (Any) -> Unit) {
        // Create a Calendar instance to get the current time
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Create a TimePickerDialog
        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            // Set the selected time in the EditText
            stime.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
        }, hour, minute, true)  // Set true for 24-hour format

        // Show the TimePickerDialog
        timePickerDialog.show()
    }
    private fun calculateCharges() {
        val startTime = stime.text.toString()
        val endTime = etime.text.toString()

        if (startTime.isNotEmpty() && endTime.isNotEmpty()) {
            val startParts = startTime.split(":").map { it.toInt() }
            val endParts = endTime.split(":").map { it.toInt() }

            val startInMinutes = startParts[0] * 60 + startParts[1]
            val endInMinutes = endParts[0] * 60 + endParts[1]

            // Calculate the duration in hours
            val durationInHours = (endInMinutes - startInMinutes) / 60.0

            // Calculate the charge
            val charge = if (durationInHours > 0) {
                (durationInHours * 800).toInt() // Rate is ₹800 per hour
            } else {
                0 // No charge if duration is less than or equal to 0
            }

            // Display the charge
            disply.text = "Total Charge: ₹$charge"
        }
    }

}
