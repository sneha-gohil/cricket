package com.example.crickethub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class formv2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formv2)
        val p2: Button =findViewById(R.id.p2)
        p2.setOnClickListener {
            val i1= Intent(this,payment::class.java)
            startActivity(i1)
        }
    }
}