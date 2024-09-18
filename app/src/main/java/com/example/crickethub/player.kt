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

        var str:String=player_name.text.toString().trim()
        var str0:String="1"
        var str1:String=age.text.toString().trim()
        var str2:String=gender.text.toString().trim()
        var str3:String=batsman.text.toString().trim()
        var str4:String=bowler.text.toString().trim()
        var str5:String=contact.text.toString().trim()

        submit.setOnClickListener {


            // Insert data into the database
          //  val result = dbhelper.insertplayer(str, age.text.toString().trim(), gender.text.toString().trim(), batsman.text.toString().trim(), bowler.text.toString().trim(), contact.text.toString().trim())
            val result = dbhelper.insertplayer(str,str0,str1,str2,str3,str4,str5)

            if (result > 0) {
                Toast.makeText(this, "Player saved successfully", Toast.LENGTH_SHORT).show()
                // Clear fields or navigate to another activity
            } else {
                Toast.makeText(this, "Error saving player", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
