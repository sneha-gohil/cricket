import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.example.crickethub.R
import com.example.crickethub.dbhelper

class bill : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        // Get the book_id from the Intent
        val bookId = intent.getLongExtra("book_id", -1)

        if (bookId != -1L) {
            // Fetch booking details from the database using book_id
            fetchAndDisplayBookingDetails(bookId)
        } else {
            Toast.makeText(this, "Error: Booking details not found.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchAndDisplayBookingDetails(bookId: Long) {
        val dbHelper = dbhelper(this)
        val db = dbHelper.readableDatabase

        // Fetch details from the database using book_id
        val cursor = db.rawQuery("SELECT * FROM book WHERE book_id = ?", arrayOf(bookId.toString()))

        if (cursor.moveToFirst()) {
            // Fetch the data from the cursor
            val userId = cursor.getString(cursor.getColumnIndexOrThrow("user_id"))
            val payId = cursor.getString(cursor.getColumnIndexOrThrow("pay_id"))
            val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
            val payMethod = cursor.getString(cursor.getColumnIndexOrThrow("pay_method"))

            // Set the fetched data to TextViews
            findViewById<TextView>(R.id.useridtext).text = userId
            findViewById<TextView>(R.id.bookidtext).text = bookId.toString()
            findViewById<TextView>(R.id.payidtext).text = payId
            findViewById<TextView>(R.id.datebill).text = date
            findViewById<TextView>(R.id.paymentmethod).text = payMethod
        } else {
            Toast.makeText(this, "No booking details found for this booking ID.", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
    }
}

