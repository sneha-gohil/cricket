package com.example.crickethub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.crickethub.com.example.crickethub.SharePrefrence

class payment : AppCompatActivity() {
    private lateinit var dbhelper: dbhelper
    private lateinit var share: SharePrefrence
    private lateinit var bookIdEditText: EditText
    private lateinit var vNameEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var startTimeEditText: EditText
    private lateinit var endTimeEditText: EditText
    private lateinit var chargeEditText: EditText
    private lateinit var noOfPlayersEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val sp1:Spinner=findViewById(R.id.sp1)
        val b2:Button=findViewById(R.id.b2)
        dbhelper = dbhelper(this)
        share = SharePrefrence(this)
        var db = dbhelper(this)
        val items = listOf("Credit Card", "Debit Card", "PayPal", "Google Pay", "Apple Pay")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sp1.adapter = adapter

        b2.setOnClickListener {
            val bookId = book_id.text.toString().trim()
            val vName = v_name.text.toString().trim()
            val dateValue = date.text.toString().trim()
            val startTimeValue = start_time.text.toString().trim()
            val endTimeValue = end_time.text.toString().trim()
            val chargeValue = charge.text.toString().trim()
            val noOfPlayersValue = no_of_player.text.toString().trim()

            // Insert data into the database
            val value = db.insertbook(bookId, vName, dateValue, startTimeValue, endTimeValue, chargeValue, noOfPlayersValue)

            if (value > 0) {
                // Save data to SharedPreferences
                share.storeUserId(bookId) // or any user ID you have
                share.venuename(vName)
                share.saveDate(dateValue)
                share.saveStartTime(startTimeValue)
                share.saveEndTime(endTimeValue)
                share.saveCharge(chargeValue)
                share.saveNoOfPlayers(noOfPlayersValue)

                Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show()
                // Optionally, redirect to another activity
                finish()
            } else {
                Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show()
            }
        }

    }
}