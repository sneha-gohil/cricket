package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class formv1 : AppCompatActivity() {
    private lateinit var dbhelper: dbhelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formv1)

        val date1: EditText = findViewById(R.id.date1)
        val stime: EditText = findViewById(R.id.stime)
        val etime: EditText = findViewById(R.id.etime)
        val charge: EditText = findViewById(R.id.charge)
        val nplayer: EditText = findViewById(R.id.nplayer)
        val p1: Button = findViewById(R.id.p1)


    }
}
