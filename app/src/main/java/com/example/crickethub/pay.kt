package com.example.crickethub

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crickethub.com.example.crickethub.SharePrefrence

class pay : AppCompatActivity() {

    private lateinit var sharePrefrence: SharePrefrence
    private lateinit var dbhelper: dbhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        val submit: Button = findViewById(R.id.submit)
        val sp1: Spinner = findViewById(R.id.sp1)

        dbhelper=dbhelper(this)
        sharePrefrence=SharePrefrence(this)

        // Initialize the spinner with payment methods
        val paymentMethods = arrayOf("Select option", "Credit Card", "Debit Card", "UPI", "Net Banking", "Cash")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp1.adapter = adapter

        submit.setOnClickListener {
            if (sp1.selectedItem == null || sp1.selectedItemPosition == 0) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
            } else {
                // Get the selected payment method
                val paymentMethod = sp1.selectedItem.toString()

                // Fetch book_id and date from SharedPreferences or intent
                val bookId = sharePrefrence.getBookId() ?: intent.getStringExtra("book_id")
                val date = sharePrefrence.getDate() ?: intent.getStringExtra("date")

                if (bookId == null || date == null) {
                    Toast.makeText(this, "No valid booking found", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Insert the payment
                val paymentId = dbhelper.insertPayment(bookId, date, paymentMethod)

                if (paymentId != -1L) {
                    // Save the pay_id and payment details to SharedPreferences
                    sharePrefrence.savepayid(paymentId.toString())

                    // Pass bookId and date to bil activity
                    val intent = Intent(this, bil::class.java)
                    intent.putExtra("book_id", bookId)
                    intent.putExtra("date", date)
                    intent.putExtra("PAYMENT_ID", paymentId)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Payment failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}
