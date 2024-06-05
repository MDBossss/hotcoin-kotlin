package android.tvz.hr.hotcoin.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.tvz.hr.hotcoin.databinding.FragmentProfileBinding
import androidx.appcompat.app.AppCompatActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // If something exists in the ViewModel constructor, there needs to be a Factory to override the create and take in that value
        val factory = ProfileViewModelFactory(requireContext())
        val profileViewModel =
            ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val logoutButton = binding.logoutButton
        logoutButton.setOnClickListener{
            profileViewModel.logout()
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}