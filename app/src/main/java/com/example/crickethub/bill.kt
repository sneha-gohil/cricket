package com.example.crickethub

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.crickethub.com.example.crickethub.SharePrefrence

class bill : AppCompatActivity() {

    private lateinit var useridTextView: TextView
    private lateinit var bookidTextView: TextView
    private lateinit var payidTextView: TextView
    private lateinit var paymentMethodTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var sharePrefrence: SharePrefrence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        // Initialize TextViews
        useridTextView = findViewById(R.id.useridtext)
        bookidTextView = findViewById(R.id.bookidtext)
        payidTextView = findViewById(R.id.payidtext)
        paymentMethodTextView = findViewById(R.id.paymentmethod)
        dateTextView = findViewById(R.id.datebill)
        sharePrefrence = SharePrefrence(this)

        // Retrieve data from Intent
        val userId = intent.getStringExtra("user_id")
        val bookId = intent.getStringExtra("book_id")
        val payId = intent.getStringExtra("pay_id")
        val paymentMethod = intent.getStringExtra("pay_method")
        val date = intent.getStringExtra("date")

        // Set data to TextViews
        useridTextView.text = userId
        bookidTextView.text = bookId
        payidTextView.text = payId
        paymentMethodTextView.text = paymentMethod
        dateTextView.text = date

    }
}
