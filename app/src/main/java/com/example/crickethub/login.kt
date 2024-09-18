package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crickethub.com.example.crickethub.SharePrefrence

class login : AppCompatActivity() {
    private lateinit var dbhelper: dbhelper
    private lateinit var share: SharePrefrence

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val register_now: TextView = findViewById(R.id.register)
        val user: EditText = findViewById(R.id.user)
        val pass: EditText = findViewById(R.id.pass)
        val login: Button = findViewById(R.id.login)
        dbhelper = dbhelper(this)
        share = SharePrefrence(this)

        register_now.setOnClickListener {
            val intent = Intent(this, registration::class.java)
            startActivity(intent)
        }
        login.setOnClickListener {
            val username = user.text.toString().trim()
            val password = pass.text.toString().trim()
            val isValidUser = dbhelper.checkUserCredentials(username, password)

                if (isValidUser !="") {
                    if (isValidUser != null) {
                        share.storeUserId(isValidUser)
                    }
                    Toast.makeText(this, "Login Succesfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, homepage::class.java)
                    startActivity(intent)
                    finish() // Optionally finish the login activity
                } else {
                    // Show error message
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


