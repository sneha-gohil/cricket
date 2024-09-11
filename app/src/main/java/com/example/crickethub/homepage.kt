package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import viewpg

class homepage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val vp:ViewPager2=findViewById(R.id.vp);
        val i1 =findViewById<ImageView>(R.id.i1);
       // val i2:ImageView=findViewById(R.id.i2);
        val adapter = viewpg(this)
        vp.adapter = adapter
        i1.setOnClickListener{
            val intent = Intent(this, slot_booking::class.java)
            startActivity(intent)
        }
    }
}