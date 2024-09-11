package com.example.crickethub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val tvv1: TextView = findViewById(R.id.tvv1);
        val tvv2: TextView = findViewById(R.id.tvv2);
        val tvv3: TextView = findViewById(R.id.tvv3);

        tvv1.setOnClickListener {
            val intent = Intent(this, slot::class.java)
            startActivity(intent)
        }
        class HomeFragment : Fragment(R.layout.fragment_home) {
        }

        class ProfileFragment : Fragment(R.layout.fragment_profile) {
        }

        class HistoryFragment : Fragment(R.layout.fragment_history) {

        }
    }
}