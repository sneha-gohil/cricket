package com.example.crickethub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment

class profile : Fragment() {

    private lateinit var dbHelper: dbhelper
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var contactEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        nameEditText = view.findViewById(R.id.name)
        emailEditText = view.findViewById(R.id.email1)
        ageEditText = view.findViewById(R.id.age1)
        contactEditText = view.findViewById(R.id.contact1)

        // Initialize database helper
        dbHelper = dbhelper(requireContext())

        // Fetch user details and display them (replace with the actual user ID or email)
        val user = dbHelper.getUserDetails(1) // Assuming user_id = 1, replace with actual logic

        if (user != null) {
            nameEditText.setText(user.name)
            ageEditText.setText(user.age.toString())
            emailEditText.setText(user.email)
            contactEditText.setText(user.contact)
        }

        return view
    }
}
