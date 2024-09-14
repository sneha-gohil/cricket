package com.example.crickethub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class formv1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formv1)
        val p1:Button=findViewById(R.id.p1)
        p1.setOnClickListener {
            val i1=Intent(this,payment::class.java)
            startActivity(i1)
        }
    }
}