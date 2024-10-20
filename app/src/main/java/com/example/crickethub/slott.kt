// MainActivity.kt
package com.example.crickethub

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class slott : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slott)


        val image1: ImageView = findViewById(R.id.imageView1)
        val image2: ImageView = findViewById(R.id.imageView2)
        val image3: ImageView = findViewById(R.id.imageView3)


        image1.setOnClickListener {
            val intent = Intent(this, form1::class.java)
            intent.putExtra("v_name", "Cricket Haven")  // Pass the venue name for Venue 1
            startActivity(intent)
        }

        image2.setOnClickListener {
            val intent = Intent(this, formv2::class.java)
            intent.putExtra("v_name", "Box Arena 360")  // Pass the venue name for Venue 2
            startActivity(intent)
        }

        image3.setOnClickListener {
            val intent = Intent(this, formv3::class.java)
            intent.putExtra("v_name", "Cricket Dome")  // Pass the venue name for Venue 3
            startActivity(intent)
        }
    }
}
