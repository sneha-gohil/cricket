package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView

class registration : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration2)
        val reg:Button=findViewById(R.id.reg);
        val et2:TextView=findViewById(R.id.et2);
        val et3:TextView=findViewById(R.id.et3);
        val et4:TextView=findViewById(R.id.et4);
        val et5:TextView=findViewById(R.id.et5);
        val pas1:TextView=findViewById(R.id.pas1);
        val c1:CheckBox=findViewById(R.id.c1);
        reg.setOnClickListener {
            if(et2.length()==0 && et3.length()==0 &&  et4.length()==0 && et5.length()==0 && pas1.length()==0 && c1.isChecked==true)
            {
                et2.setError("fill this")
                et3.setError("fill this")
                et4.setError("fill this")
                et5.setError("fill this")
                pas1.setError("fill this")
                c1.setError("should be checked")
            }
            else {
                val intent = Intent(this, login::class.java)
                startActivity(intent)
            }
        }
    }
}