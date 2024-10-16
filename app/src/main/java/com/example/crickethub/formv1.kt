package com.example.crickethub

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crickethub.com.example.crickethub.SharePrefrence
import java.text.SimpleDateFormat
import java.util.*

class formv1 : AppCompatActivity() {
    private lateinit var stime: EditText
    private lateinit var etime: EditText
    private lateinit var display: TextView
    private lateinit var date1:EditText
    private val calendar = Calendar.getInstance()
    private lateinit var sharePrefrence: SharePrefrence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formv1)

        val date1: EditText = findViewById(R.id.date1)
        stime = findViewById(R.id.stime)
        etime = findViewById(R.id.etime)
        val nplayer: EditText = findViewById(R.id.nplayer)
        display = findViewById(R.id.display)
        val p1: Button = findViewById(R.id.p1)

        stime.setOnClickListener {
            showTimePickerDialog(stime)
        }

        etime.setOnClickListener {
            showTimePickerDialog(etime)
        }

        sharePrefrence=SharePrefrence(this)

        fun showDatePickerDialog() {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                // Update the calendar object with the selected date
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Format the date and set it in the EditText
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(calendar.time)

                // Set the formatted date to the EditText (converted to Editable)
                date1.text = Editable.Factory.getInstance().newEditable(formattedDate)
            }

            // Show the DatePickerDialog
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        date1.setOnClickListener {
            showDatePickerDialog()
        }

        // Calculate charges when end_time is entered
        etime.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (stime.text.isNotEmpty() && etime.text.isNotEmpty()) {
                    val totalHours = calculateTimeDifferenceInHours(stime.text.toString(), etime.text.toString())
                    if (totalHours > 0) {
                        val totalCharges = totalHours * 800
                        display.text = "Total Charges: Rs. $totalCharges"
                    } else {
                        Toast.makeText(this, "Invalid time range", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    display.text = ""
                }
            }
        }

        // Navigate to payment activity on button click
        p1.setOnClickListener {
            if (stime.text.isNotEmpty() && etime.text.isNotEmpty() && display.text.isNotEmpty()) {
                // Get total charges
                val totalCharges = display.text.toString().split("Rs. ")[1]

                // Venue name (can be dynamically selected or hardcoded)
                val venueName = "Cricket haven"

                // Insert booking details into the database
                val dbHelper = dbhelper(this)
                val result = dbHelper.insertbook(
                    v_name = venueName,
                    date = date1.text.toString(),
                    startTime = stime.text.toString(),
                    endtime = etime.text.toString(),
                    charge = totalCharges,
                    no_of_player = nplayer.text.toString()
                )

                if (result > 0) {
                    // Store user_id in SharedPreferences
                    val userId = sharePrefrence.getUserId() ?: "Unknown"  // Assuming it's fetched or hardcoded for now

                    // Save details in SharedPreferences
                    sharePrefrence.saveBookId(result.toString())
                    sharePrefrence.saveDate(date1.text.toString())
                    sharePrefrence.saveStartTime(stime.text.toString())
                    sharePrefrence.saveEndTime(etime.text.toString())
                    sharePrefrence.saveNoOfPlayers(nplayer.text.toString())

                    // Pass details to Payment Activity
                    val intent = Intent(this, pay::class.java).apply {
                        putExtra("book_id", result.toString())  // Auto-incremented book_id
                        putExtra("date", date1.text.toString())
                        putExtra("start_time", stime.text.toString())
                        putExtra("end_time", etime.text.toString())
                        putExtra("no_of_players", nplayer.text.toString())
                        putExtra("charges", totalCharges)
                        putExtra("user_id", userId)  // Include user_id in the intent
                    }
                    startActivity(intent)
                }


            } else {
                Toast.makeText(this, "Please fill all fields and calculate charges", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showTimePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this,
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                editText.setText(formattedTime)
            },
            hour, minute, true)
        timePickerDialog.show()
    }

    private fun calculateTimeDifferenceInHours(startTime: String, endTime: String): Long {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val start = format.parse(startTime)!!
        val end = format.parse(endTime)!!
        val differenceInMillis = end.time - start.time
        return differenceInMillis / (1000 * 60 * 60) // Convert milliseconds to hours
    }
}
