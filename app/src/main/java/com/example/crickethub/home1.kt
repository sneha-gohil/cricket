package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class home1 : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View= inflater.inflate(R.layout.fragment_home1, container, false)
        val b1:Button= view.findViewById(R.id.b1);
        val b2:Button= view.findViewById(R.id.b2);
        val b3:TextView= view.findViewById(R.id.b3);

        b1.setOnClickListener {
            val i1= Intent (requireContext(), slott::class.java)
            startActivity(i1)
        }
        b2.setOnClickListener {
            val i2= Intent (requireContext(), player::class.java)
            startActivity(i2)
        }
        b3.setOnClickListener {
            val i2= Intent (requireContext(), learnmore::class.java)
            startActivity(i2)
        }
        return view
    }
}
