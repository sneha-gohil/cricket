package com.example.crickethub

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.crickethub.com.example.crickethub.SharePrefrence

class payment : AppCompatActivity() {

    private lateinit var paymentSpinner: Spinner
    private lateinit var submitButton: Button
    private lateinit var dbhelper: dbhelper
    private lateinit var SharePrefrence:SharePrefrence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)

        dbhelper= dbhelper(this)
        SharePrefrence = SharePrefrence(this)

        paymentSpinner = findViewById(R.id.sp1)
        submitButton = findViewById(R.id.submit)

        // Set up the Spinner with payment options
        val paymentMethods = arrayOf("Select Payment Method", "Credit Card", "Debit Card", "UPI", "Net Banking", "Cash")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentSpinner.adapter = adapter

        submitButton.setOnClickListener {
            val selectedPaymentMethod = paymentSpinner.selectedItem.toString()

            // Ensure a valid payment method is selected
            if (selectedPaymentMethod == "Select Payment Method") {
                Toast.makeText(this, "Please select a payment method!", Toast.LENGTH_SHORT).show()
            } else {
                // Insert the payment details into the database
                insertPaymentDetails(selectedPaymentMethod)
            }
        }
    }

    private fun insertPaymentDetails(paymentMethod: String) {
        // Retrieve data from SharedPreferences or other sources
        val bookId = SharePrefrence.getBookId()?.toInt() ?: -1
        val userId = SharePrefrence.getUserId()?.toInt() ?: -1
        val bookingDate = SharePrefrence.getDate() ?: ""
        val paymentDate = bookingDate // assuming payment is made on booking date

        // Ensure valid data before inserting
        if (bookId == -1 || userId == -1 || bookingDate.isEmpty()) {
            Toast.makeText(this, "Error retrieving booking details. Please try again.", Toast.LENGTH_SHORT).show()
            return
        }

        // Insert into the payment table
        val result = dbhelper.insertPaymentDetails(bookId.toString(),
            userId.toString(), paymentDate, paymentMethod)

        if (result != -1L) {
            // Data inserted successfully, proceed to BillActivity
            Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show()
            startBillActivity(paymentMethod)
        } else {
            // Error in data insertion
            Toast.makeText(this, "Error processing payment. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startBillActivity(paymentMethod: String) {
        val intent = Intent(this, bill::class.java)
        intent.putExtra("PAYMENT_METHOD", paymentMethod)

        // Optional: You can pass other details like total charges
        val totalCharges = intent.getStringExtra("TOTAL_CHARGES") // Get it from intent or other sources
        intent.putExtra("TOTAL_CHARGES", totalCharges)

        startActivity(intent)
    }
}
