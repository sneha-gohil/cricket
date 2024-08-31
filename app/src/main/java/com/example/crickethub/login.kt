package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class login : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val tv1:TextView=findViewById(R.id.tv1);
        val cond=""
        val et1:TextView=findViewById(R.id.et1);
        val pass:TextView=findViewById(R.id.pass);
        val b1:Button=findViewById(R.id.b1);
        tv1.setOnClickListener {
            val intent = Intent(this, registration::class.java)
            startActivity(intent)
        }
        b1.setOnClickListener {
            if(et1.length()==0 && pass.length()==0)
            {
                et1.setError("fill this")
                pass.setError("fill this")
            }
            else {
                val intent = Intent(this, homepage::class.java)
                startActivity(intent)
            }
        }

    }
}