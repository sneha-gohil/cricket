package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crickethub.com.example.crickethub.SharePrefrence

class pay : AppCompatActivity() {
    private lateinit var dbhelper: dbhelper
    private lateinit var sharePrefrence: SharePrefrence

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pay)

        val submit: Button = findViewById(R.id.submit)
        val download: Button = findViewById(R.id.download)
        val showreceipt: Button = findViewById(R.id.showreceipt)
        val sp1: Spinner = findViewById(R.id.sp1)
        val display: TextView = findViewById(R.id.display)

        dbhelper = dbhelper(this)
        sharePrefrence = SharePrefrence(this)

        val userId = sharePrefrence.getUserId()  // Assuming you have this method
        val sharedVName = sharePrefrence.getvenuename("Unknown")

        val date = intent.getStringExtra("date") ?: sharePrefrence.getDate()
        val startTime = intent.getStringExtra("start_time") ?: sharePrefrence.getStartTime()
        val endTime = intent.getStringExtra("end_time") ?: sharePrefrence.getEndTime()
        val noOfPlayers = intent.getStringExtra("no_of_players") ?: sharePrefrence.getNoOfPlayers()
        val charges = intent.getStringExtra("charges")

        display.text = "Total Charge: Rs. $charges"

        val paymentMethods = arrayOf("Credit Card", "Debit Card", "UPI", "Net Banking", "Cash")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp1.adapter = adapter

        submit.setOnClickListener {

        }
    }
}

