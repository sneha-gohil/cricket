import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crickethub.R
import com.example.crickethub.dbhelper

class adminpage : AppCompatActivity() {

    private lateinit var dbhelper: dbhelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adminadapter: adminadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminpage)

        dbhelper = dbhelper(this)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewAdmin)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch bookings from database
        val bookingList = dbhelper.getAllBookings()

        // Set adapter
        adminadapter=adminadapter(bookingList)
        recyclerView.adapter = adminadapter
    }
}
