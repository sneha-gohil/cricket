package com.example.crickethub

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class home : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:  View= inflater.inflate(R.layout.fragment_home, container, false)
        val i1=view.findViewById<ImageView>(R.id.i1)
        i1.setOnClickListener{
            val intent = Intent(requireContext(), slot::class.java)
            startActivity(intent)
        }
        return view
    }
}