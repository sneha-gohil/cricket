package com.example.crickethub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.crickethub.com.example.crickethub.SharePrefrence

class player : AppCompatActivity() {
    private lateinit var dbhelper: dbhelper
    private lateinit var share: SharePrefrence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        share = SharePrefrence(this)
        dbhelper = dbhelper(this)

        val userId = share.getUserId()

        val player_name: EditText = findViewById(R.id.player_name)
        val age: EditText = findViewById(R.id.age)
        val gender: EditText = findViewById(R.id.gender)
        val batsman: EditText = findViewById(R.id.batsman)
        val bowler: EditText = findViewById(R.id.bowler)
        val contact: EditText = findViewById(R.id.contact)
        val submit: Button = findViewById(R.id.submit)

        submit.setOnClickListener {
            // Insert data into the database
            val result = userId?.let { it1 ->
                dbhelper.insertplayer(
                    it1,
                    player_name.text.toString().trim(),
                    age.text.toString().trim(),
                    gender.text.toString().trim(),
                    batsman.text.toString().trim(),
                    bowler.text.toString().trim(),
                    contact.text.toString().trim()
                )
            }

            if (result != null) {
                if (result > 0) {
                    Toast.makeText(this, "Player saved successfully", Toast.LENGTH_SHORT).show()
                    // Clear fields or navigate to another activity
                } else {
                    Toast.makeText(this, "Error saving player", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
