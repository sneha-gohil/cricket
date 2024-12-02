package com.example.crickethub

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.crickethub.com.example.crickethub.SharePrefrence

class adhome : AppCompatActivity() {

    private lateinit var editTextBookId: TextView
    private lateinit var editTextPaymentMethod: TextView
    private lateinit var editTextDate: TextView
    private lateinit var sharePreference: SharePrefrence
    private lateinit var llt:LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adhome)

        // Initialize UI elements
        editTextBookId = findViewById(R.id.editTextBookId)
        editTextPaymentMethod = findViewById(R.id.editTextPaymentMethod)
        editTextDate = findViewById(R.id.editTextDate)
        val llt: LinearLayout = findViewById(R.id.llt)


        // Initialize SharedPreferences helper
        sharePreference = SharePrefrence(this)

        // Fetch data from SharedPreferences
        val lastBookId = sharePreference.getBookId()
        val lastPaymentMethod = sharePreference.getPaymentMethod()
        val lastDate = sharePreference.getdate()

        // Display data in EditTexts
        editTextBookId.setText(lastBookId)
        editTextPaymentMethod.setText(lastPaymentMethod)
        editTextDate.setText(lastDate)

        llt.setOnClickListener {
            showDeleteAlertDialog()
        }
    }

    private fun showDeleteAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Data")
        builder.setMessage("Are you sure you want to delete all data?")

        // Set the positive button to "Yes"
        builder.setPositiveButton("Yes") { dialog, _ ->
            resetFields()
            dialog.dismiss()
        }

        // Set the negative button to "No"
        builder.setNegativeButton("No") { dialog, _ ->
            resetFields()
            dialog.dismiss()
        }

        // Show the AlertDialog
        val alertDialog = builder.create()
        alertDialog.show()
    }

    // Function to reset the displayed fields
    private fun resetFields() {
        editTextBookId.text = ""
        editTextPaymentMethod.text = ""
        editTextDate.text = ""
    }
    }

