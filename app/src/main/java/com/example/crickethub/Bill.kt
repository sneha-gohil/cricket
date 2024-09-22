package com.example.crickethub

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crickethub.com.example.crickethub.SharePrefrence

class Bill : AppCompatActivity() {
    private lateinit var sharePreference: SharePrefrence
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bill)
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", -1) // Default to -1 if not found

    }
}