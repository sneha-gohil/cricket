package com.example.crickethub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.crickethub.com.example.crickethub.SharePrefrence

class profile : Fragment() {

    private lateinit var dbHelper: dbhelper
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var contactEditText: EditText
    private lateinit var sharePrefrence: SharePrefrence

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
        val updateButton:Button=view.findViewById(R.id.updateButton)

        // Initialize database helper
        dbHelper = dbhelper(requireContext())
        sharePrefrence = SharePrefrence(requireContext())

        val userId = sharePrefrence.getUserId()


        val db = context?.let { dbhelper(it) }

        updateButton.setOnClickListener {
            val userName = nameEditText.text.toString()
            val age = ageEditText.text.toString()
            val email = emailEditText.text.toString()
            val contact = contactEditText.text.toString()

            // Call the update function
            val result = userId?.let { it1 -> db?.updateUser(it1, userName, age, email, contact) }

            if (result != null) {
                if (result > 0) {
                    Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to update profile!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (userId != null) {
            // User has already registered or logged in, fetch profile from database
            val user = dbHelper.getUserDetails(userId)

            if (user != null) {
                // Display user profile details
                nameEditText.setText(user.name)
                ageEditText.setText(user.age.toString())
                emailEditText.setText(user.email)
                contactEditText.setText(user.contact)
            } else {
                Toast.makeText(requireContext(), "User profile not found", Toast.LENGTH_SHORT).show()
            }
        } else {
            // No user registered, handle new registration (or redirect to registration form)
            Toast.makeText(requireContext(), "No user logged in. Please register.", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
