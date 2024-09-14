package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class registration : AppCompatActivity() {
    private lateinit var db : dbhelper

        @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration2)
        val reg: Button = findViewById(R.id.reg);
        val user : EditText = findViewById(R.id.username)
        val email : EditText = findViewById(R.id.email)
        val age : EditText = findViewById(R.id.age)
        val phone : EditText = findViewById(R.id.no)
        val pass : EditText = findViewById(R.id.pass)
        val c1: CheckBox = findViewById(R.id.c1)
            db = dbhelper(this)
        reg.setOnClickListener {
           val value = db.insertUser(user.text.toString().trim(),age.text.toString().trim(),email.text.toString().trim(),phone.text.toString().trim(),pass.text.toString().trim())
            if (value>0){
                Toast.makeText(this,"Registration successfull",Toast.LENGTH_SHORT).show()
                val i = Intent(this,login::class.java)
                startActivity(i)
                finish()
            }else{
                Toast.makeText(this,"Registration failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}



