package com.example.crickethub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class formv3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formv3)
        val p3: Button =findViewById(R.id.p3)
        p3.setOnClickListener {
            val i1= Intent(this,payment::class.java)
            startActivity(i1)
        }
    }
}