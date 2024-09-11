import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.crickethub.history
import com.example.crickethub.home
import com.example.crickethub.profile

class viewpg(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> home()
            1 -> profile()
            2 -> history()
            else -> home()
        }
    }
    override fun getItemCount(): Int = 3 // Number of fragments
}