package com.example.crickethub

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.crickethub.com.example.crickethub.SharePrefrence
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class form1 : AppCompatActivity() {

    private lateinit var dbhelper: dbhelper
    private lateinit var date1: EditText
    private lateinit var stime: EditText
    private lateinit var etime: EditText
    private lateinit var disply: TextView
    private lateinit var nplayer:EditText
    private lateinit var venueName: TextView
    private lateinit var sharePrefrence: SharePrefrence

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form1)

        dbhelper=dbhelper(this)
        sharePrefrence=SharePrefrence(this)

        date1 = findViewById(R.id.date1)
        stime = findViewById(R.id.stime)
        etime = findViewById(R.id.etime)
        nplayer= findViewById(R.id.nplayer)
        disply = findViewById(R.id.disply)
        val p1: Button = findViewById(R.id.p1)
        venueName = findViewById(R.id.tv1)

        val userId=sharePrefrence.getUserId()

        val vName = intent.getStringExtra("v_name")
        venueName.text = vName


        date1.setOnClickListener {
            showDatePickerDialog()
        }
        stime.setOnClickListener {
            showTimePickerDialog { selectedTime ->
                stime.setText(String.format("%02d:%02d", selectedTime.hour, selectedTime.minute))
                calculateCharges()
            }
        }
        etime.setOnClickListener {
            showTimePickerDialog { selectedTime ->
                etime.setText(String.format("%02d:%02d", selectedTime.hour, selectedTime.minute))
                calculateCharges()
            }
        }
        p1.setOnClickListener {
            val v_name = venueName.text.toString()
            val date = date1.text.toString()
            val start_time = stime.text.toString()
            val end_Time = etime.text.toString()
            val charge = disply.text.toString().replace("Total Charge: ₹", "")
            val no_of_player = nplayer.text.toString()

            val result = dbhelper.insertbook(v_name, date, start_time, end_Time, charge, no_of_player)
            if (result > 0) {

                sharePrefrence.saveBookId(result.toString())
                sharePrefrence.saveDate(date)

                Toast.makeText(this,"insert success",Toast.LENGTH_LONG).show()
                val intent = Intent(this, payment::class.java)
                intent.putExtra("user_id",userId)
                intent.putExtra("book_id",result.toString())
                intent.putExtra("date",date)

                startActivity(intent)
            } else {
                Toast.makeText(this,"insert not success",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            date1.setText(String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear))
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog(onTimeSet: (Time) -> Unit) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val selectedTime = Time(selectedHour, selectedMinute)
            onTimeSet(selectedTime)
        }, hour, minute, true)

        timePickerDialog.show()
    }

    private fun calculateCharges() {
        val startTime = stime.text.toString()
        val endTime = etime.text.toString()
        if (startTime.isNotEmpty() && endTime.isNotEmpty()) {
            val startParts = startTime.split(":").map { it.toInt() }
            val endParts = endTime.split(":").map { it.toInt() }
            val startInMinutes = startParts[0] * 60 + startParts[1]
            val endInMinutes = endParts[0] * 60 + startParts[1]
            val durationInHours = (endInMinutes - startInMinutes) / 60.0
            val charge = if (durationInHours > 0) {
                (durationInHours * 800).toInt()
            } else {
                0
            }
            disply.text = "Total Charge: ₹$charge"
        }
    }
    data class Time(val hour: Int, val minute: Int)
}
