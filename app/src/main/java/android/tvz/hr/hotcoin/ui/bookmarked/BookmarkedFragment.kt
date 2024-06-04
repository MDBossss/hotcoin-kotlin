package android.tvz.hr.hotcoin.ui.bookmarked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.tvz.hr.hotcoin.databinding.FragmentBookmarkedBinding

class BookmarkedFragment : Fragment() {

    private var _binding: FragmentBookmarkedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bookmarkedViewModel =
            ViewModelProvider(this).get(BookmarkedViewModel::class.java)

        _binding = FragmentBookmarkedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBookmarked
        bookmarkedViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}