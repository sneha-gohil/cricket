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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pay)

        val submit: Button = findViewById(R.id.submit)
        val download: Button = findViewById(R.id.download)
        val showreceipt: Button = findViewById(R.id.showreceipt)
        val sp1: Spinner = findViewById(R.id.sp1)

        val paymentMethods = arrayOf("Select option","Credit Card", "Debit Card", "UPI", "Net Banking", "Cash")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp1.adapter = adapter

        submit.setOnClickListener {
            if (sp1.selectedItem == null || sp1.selectedItemPosition == 0) {
                Toast.makeText(this, "Please select a venue", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error submitting", Toast.LENGTH_SHORT).show()
            }
        }

    }
}


