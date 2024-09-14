package com.example.crickethub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class slot : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slot)

        val img1:ImageView=findViewById(R.id.imageView1)
        val img2:ImageView=findViewById(R.id.imageView2)
        val img3:ImageView=findViewById(R.id.imageView3)
        img1.setOnClickListener{
            val intent = Intent(this, formv1::class.java)
            startActivity(intent)
        }
        img2.setOnClickListener{
            val intent = Intent(this, formv2::class.java)
            startActivity(intent)
        }
        img3.setOnClickListener{
            val intent = Intent(this, formv3::class.java)
            startActivity(intent)
        }
    }
}