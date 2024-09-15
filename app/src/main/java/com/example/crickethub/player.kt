package com.example.crickethub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class player : AppCompatActivity() {
    private lateinit var dbhelper: dbhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        dbhelper = dbhelper(this)

        val player_name: EditText = findViewById(R.id.player_name)
        val age: EditText = findViewById(R.id.age)
        val gender: EditText = findViewById(R.id.gender)
        val batsman: EditText = findViewById(R.id.batsman)
        val bowler: EditText = findViewById(R.id.bowler)
        val contact: EditText = findViewById(R.id.contact)
        val submit: Button = findViewById(R.id.submit)

        submit.setOnClickListener {
            val player_name = player_name.text.toString().trim()
            val age = age.text.toString().trim()
            val gender = gender.text.toString().trim()
            val batsman = batsman.text.toString().trim()
            val bowler = bowler.text.toString().trim()
            val contact = contact.text.toString().trim()

            if (player_name.isEmpty() || age == null || gender.isEmpty() || batsman.isEmpty() || bowler.isEmpty() || contact.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insert data into the database
            val result = dbhelper.insertPlayer(player_name, age, gender, batsman, bowler, contact)

            if (result > 0) {
                Toast.makeText(this, "Player saved successfully", Toast.LENGTH_SHORT).show()
                // Clear fields or navigate to another activity
            } else {
                Toast.makeText(this, "Error saving player", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
