package com.example.crickethub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class payment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val sp1:Spinner=findViewById(R.id.sp1)
        val b2:Button=findViewById(R.id.b2)
        val items = listOf("Credit Card", "Debit Card", "PayPal", "Google Pay", "Apple Pay")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sp1.adapter = adapter

        b2.setOnClickListener {

        }

    }
}