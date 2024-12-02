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

        val userId = intent.getStringExtra("user_id")
        val bookId = intent.getStringExtra("book_id")
        val date = intent.getStringExtra("date")
        val vName=intent.getStringExtra("v_name")

        val paymentMethods = arrayOf("Select Payment Method", "Credit Card", "Debit Card", "UPI", "Net Banking", "Cash")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentSpinner.adapter = adapter

        submitButton.setOnClickListener {
            val pay_method = paymentSpinner.selectedItem.toString()

            if (pay_method == "Select Payment Method") {
                Toast.makeText(this, "Please select a payment method!", Toast.LENGTH_SHORT).show()
            } else {
                val payId = dbhelper.insertPayment(userId.toString(),
                    bookId.toString(), pay_method, date.toString()
                )

                if (payId > 0) {
                    // Store pay_id and payment method in SharedPreferences
                    SharePrefrence.savepayid(payId.toString())
                    SharePrefrence.savePaymentMethod(pay_method)

                    // Pass data to Bill activity using Intent
                    val intent = Intent(this, bill::class.java)
                    intent.putExtra("user_id", userId)
                    intent.putExtra("book_id", bookId)
                    intent.putExtra("date", date)
                    intent.putExtra("pay_method", pay_method)
                    intent.putExtra("pay_id", payId.toString())
                    Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show()

                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Payment failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
