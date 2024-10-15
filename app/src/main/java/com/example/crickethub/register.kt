package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class register : AppCompatActivity() {
    private lateinit var dbhelper: dbhelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        val reg: Button = findViewById(R.id.reg)
        val user: EditText = findViewById(R.id.username)
        val email: EditText = findViewById(R.id.email)
        val age: EditText = findViewById(R.id.age)
        val phone: EditText = findViewById(R.id.no)
        val pass: EditText = findViewById(R.id.pass)
        val c1: CheckBox = findViewById(R.id.c1)

        dbhelper = dbhelper(this)

        reg.setOnClickListener {
            val username = user.text.toString().trim()
            val userEmail = email.text.toString().trim()
            val userAge = age.text.toString().trim()
            val userPhone = phone.text.toString().trim()
            val userPassword = pass.text.toString().trim()

            // Validate all fields
            if (validateInputs(username, userEmail, userAge, userPhone, userPassword, c1)) {
                // Insert into DB
                val value = dbhelper.insertUser(username, userAge, userEmail, userPhone, userPassword)
                if (value > 0) {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, login::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Function to validate input fields
    private fun validateInputs(
        username: String,
        email: String,
        age: String,
        phone: String,
        password: String,
        termsCheckBox: CheckBox
    ): Boolean {
        // Check if username is empty
        if (username.isEmpty()) {
            showError("Username cannot be empty")
            return false
        }

        // Check if email is valid
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Please enter a valid email")
            return false
        }

        // Check if age is valid
        if (age.isEmpty()) {
            showError("Age cannot be empty")
            return false
        } else {
            val ageInt = age.toIntOrNull()
            if (ageInt == null || ageInt < 18 || ageInt > 100) {
                showError("Please enter a valid age between 18 and 100")
                return false
            }
        }

        // Check if phone is valid
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length != 10) {
            showError("Please enter a valid 10-digit phone number")
            return false
        }

        // Check if password is valid
        if (password.isEmpty() || password.length < 6) {
            showError("Password must be at least 6 characters long")
            return false
        }

        // Check if terms and conditions checkbox is checked
        if (!termsCheckBox.isChecked) {
            showError("You must accept the terms and conditions")
            return false
        }

        return true
    }

    // Helper function to show error messages
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
