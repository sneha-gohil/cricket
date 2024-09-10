package com.example.crickethub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val tvv1: TextView = findViewById(R.id.tvv1);
        val tvv2: TextView = findViewById(R.id.tvv2);
        val tvv3: TextView = findViewById(R.id.tvv3);

        tvv1.setOnClickListener{
            val intent = Intent(this, slot::class.java)
            startActivity(intent)
        }
    }
}