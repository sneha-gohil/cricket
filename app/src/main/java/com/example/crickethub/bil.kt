package com.example.crickethub

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crickethub.com.example.crickethub.SharePrefrence

class bil : AppCompatActivity() {

    private lateinit var sharePrefrence: SharePrefrence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bil)

        val tvUserId: TextView = findViewById(R.id.useridtext)
        val tvBookId: TextView = findViewById(R.id.bookidtext)
        val tvPayId: TextView = findViewById(R.id.payidtext)
        val tvPaymentMethod: TextView = findViewById(R.id.paymentmethod)
        val tvDate: TextView = findViewById(R.id.datebill)

        // Initialize SharedPreferences
        val bookId = intent.getStringExtra("book_id")
        val date = intent.getStringExtra("date")

        sharePrefrence= SharePrefrence(this)

        // Retrieve stored booking and payment details from SharedPreferences
        val userId = sharePrefrence.getUserId() ?: "N/A"
        val payId = sharePrefrence.getpayid() ?: "N/A"
        val paymentMethod = sharePrefrence.getPaymentMethod() ?: "N/A"

        // Find TextViews in the layout to display the details


        // Set the text for each TextView with the retrieved details
        tvUserId.text = "$userId"
        tvBookId.text = "$bookId"
        tvPayId.text = "$payId"
        tvPaymentMethod.text = "$paymentMethod"
        tvDate.text = "$date"
    }
}



