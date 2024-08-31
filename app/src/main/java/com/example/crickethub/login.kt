package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class login : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tv1: TextView = findViewById(R.id.tv1)
        val et1: EditText = findViewById(R.id.et1)
        val pass: EditText = findViewById(R.id.pass)
        val b1: Button = findViewById(R.id.b1)

        tv1.setOnClickListener {
            val intent = Intent(this, registration::class.java)
            startActivity(intent)
        }

        b1.setOnClickListener {
            val username = et1.text.toString()
            val password = pass.text.toString()

            // Check if username or password is empty
            if (username.isEmpty()) {
                et1.error = "Please enter your username"
            } else if (password.isEmpty()) {
                pass.error = "Please enter your password"
            } else {
                // Validate password directly in the click listener
                var isValid = true
                for (char in password) {
                    if (!char.isLowerCase() && !char.isDigit() && char != '_' && char != '.') {
                        isValid = false
                        break
                    }
                }

                if (!isValid) {
                    pass.error = "Password must contain only lowercase letters, digits, underscores, or dots"
                } else {
                    val intent = Intent(this, homepage::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
