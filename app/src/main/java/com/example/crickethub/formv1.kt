package com.example.crickethub

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.crickethub.com.example.crickethub.SharePrefrence
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class formv1 : AppCompatActivity() {

    private lateinit var dateEditText: EditText
    private lateinit var stimeEditText: EditText
    private lateinit var etimeEditText: EditText
    private lateinit var displayTextView: TextView
    private val chargePerHour = 800
    private lateinit var dbhelper: dbhelper
    private lateinit var SharePrefrence: SharePrefrence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formv1)

        dbhelper = dbhelper(this)
        SharePrefrence = SharePrefrence(this)

        dateEditText = findViewById(R.id.date1)
        stimeEditText = findViewById(R.id.stime)
        etimeEditText = findViewById(R.id.etime)
        displayTextView = findViewById(R.id.display)
        val paymentButton: Button = findViewById(R.id.pay1)

        dateEditText.setOnClickListener { showDatePickerDialog(dateEditText) }
        stimeEditText.setOnClickListener { showTimePickerDialog(stimeEditText) }
        etimeEditText.setOnClickListener {
            showTimePickerDialog(etimeEditText) {
                calculateAndDisplayCharges()
            }
        }

        paymentButton.setOnClickListener {
            if (validateTimes()) {
                calculateAndDisplayCharges()
                insertDataIntoDatabase()
                startPaymentActivity()
            } else {
                displayTextView.text = "Invalid time selection!"
            }
        }
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
                editText.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePickerDialog(editText: EditText, onTimeSet: (() -> Unit)? = null) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                editText.setText(selectedTime)
                onTimeSet?.invoke()
            },
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
        ).show()
    }

    private fun validateTimes(): Boolean {
        return try {
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val startTime = timeFormat.parse(stimeEditText.text.toString())
            val endTime = timeFormat.parse(etimeEditText.text.toString())
            startTime != null && endTime != null && endTime.after(startTime)
        } catch (e: Exception) {
            false
        }
    }

    private fun calculateAndDisplayCharges() {
        try {
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val startTime = timeFormat.parse(stimeEditText.text.toString())
            val endTime = timeFormat.parse(etimeEditText.text.toString())

            val differenceInMillis = endTime!!.time - startTime!!.time
            val hours = differenceInMillis / (1000 * 60 * 60).toFloat()

            val totalCharges = hours * chargePerHour
            displayTextView.text = "Total Charges: Rs. $totalCharges"
        } catch (e: Exception) {
            displayTextView.text = "Error calculating charges!"
        }
    }

    private fun insertDataIntoDatabase() {
        val vName = SharePrefrence.getvenuename("") ?: "Unknown Venue"
        val date = dateEditText.text.toString()
        val startTime = stimeEditText.text.toString()
        val endTime = etimeEditText.text.toString()
        val charges = displayTextView.text.toString().replace("Total Charges: Rs. ", "") ?: 0f
        val noOfPlayers = SharePrefrence.getNoOfPlayers()?.toIntOrNull() ?: 0

        // Insert only if all data is valid
        if (vName.isNotEmpty() && date.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty() && charges > 0 as Nothing && noOfPlayers > 0) {
            val result = dbhelper.insertbook(vName, date, startTime, endTime, charges.toString(), noOfPlayers.toString())
            if (result != -1L) {
                displayTextView.text = "Booking Successful!"
            } else {
                displayTextView.text = "Database Insertion Failed!"
            }
        } else {
            displayTextView.text = "Error: Missing or invalid data!"
        }
    }

    private fun startPaymentActivity() {
        val totalCharges = displayTextView.text.toString()
        val intent = Intent(this, payment::class.java)
        intent.putExtra("TOTAL_CHARGES", totalCharges)
        startActivity(intent)
    }
}
