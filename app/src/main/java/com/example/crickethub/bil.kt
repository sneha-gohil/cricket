package com.example.crickethub

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class bil : AppCompatActivity() {

    private lateinit var dbHelper: dbhelper
    private lateinit var tvUserId: TextView
    private lateinit var tvBookId: TextView
    private lateinit var tvPayId: TextView
    private lateinit var tvPaymentMethod: TextView
    private lateinit var tvDate: TextView
    private lateinit var btnDownloadReceipt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bil)

        dbHelper = dbhelper(this)

        // Initialize TextViews and Button
        tvUserId = findViewById(R.id.useridtext)
        tvBookId = findViewById(R.id.bookidtext)
        tvPayId = findViewById(R.id.payidtext)
        tvPaymentMethod = findViewById(R.id.paymentmethod)
        tvDate = findViewById(R.id.datebill)
        btnDownloadReceipt = findViewById(R.id.button)

        // Get payment ID from the intent
        val paymentId = intent.getLongExtra("PAYMENT_ID", -1)

        // Fetch and display the bill details
        if (paymentId != -1L) {
            loadBillDetails(paymentId)
        } else {
            // Handle the case where payment ID is invalid
        }

        // Set a click listener for download receipt (you can implement this to save or share the receipt)
        btnDownloadReceipt.setOnClickListener {
            // Implement download receipt functionality here
        }
    }

    private fun loadBillDetails(payId: Long) {
        // Fetch the details from the database
        val cursor = dbHelper.getBillDetails(payId)

        cursor?.let { cursor ->
            if (cursor.moveToFirst()) {
                // Fetch user details
                val userId = cursor.getString(cursor.getColumnIndexOrThrow("user_id"))
                val bookId = cursor.getString(cursor.getColumnIndexOrThrow("book_id"))
                val payId = cursor.getString(cursor.getColumnIndexOrThrow("pay_id"))
                val paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow("pay_method"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))

                // Set the values to the TextViews
                tvUserId.text = userId
                tvBookId.text = bookId
                tvPayId.text = payId
                tvPaymentMethod.text = paymentMethod
                tvDate.text = date
            }
            cursor.close()
        }
    }
}
