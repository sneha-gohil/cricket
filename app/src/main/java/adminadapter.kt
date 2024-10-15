import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crickethub.R

data class BookingData(val userId: String, val bookId: String, val payMethod: String, val date: String)


class adminadapter(private val bookingList: List<BookingData>) :
    RecyclerView.Adapter<adminadapter.BookingViewHolder>() {

    inner class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUserId: TextView = view.findViewById(R.id.tvUserId)
        val tvBookId: TextView = view.findViewById(R.id.tvBookId)
        val tvPayMethod: TextView = view.findViewById(R.id.tvPayMethod)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyleview, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]
        holder.tvUserId.text = booking.userId
        holder.tvBookId.text = booking.bookId
        holder.tvPayMethod.text = booking.payMethod
        holder.tvDate.text = booking.date
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }
}