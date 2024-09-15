// MainActivity.kt
package com.example.crickethub

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slot)


        val image1: ImageView = findViewById(R.id.imageView1)
        val image2: ImageView = findViewById(R.id.imageView2)
        val image3: ImageView = findViewById(R.id.imageView3)


        image1.setOnClickListener {
            val intent = Intent(this, formv1::class.java)
            intent.putExtra("v_name", "Venue 1")  // Pass the venue name for Venue 1
            startActivity(intent)
        }

        image2.setOnClickListener {
            val intent = Intent(this, formv2::class.java)
            intent.putExtra("v_name", "Venue 2")  // Pass the venue name for Venue 2
            startActivity(intent)
        }

        image3.setOnClickListener {
            val intent = Intent(this, formv3::class.java)
            intent.putExtra("v_name", "Venue 3")  // Pass the venue name for Venue 3
            startActivity(intent)
        }
    }
}
