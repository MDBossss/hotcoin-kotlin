package android.tvz.hr.hotcoin.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.tvz.hr.hotcoin.databinding.FragmentProfileBinding
import android.tvz.hr.hotcoin.local.UserDao
import android.tvz.hr.hotcoin.local.UserDatabase
import android.tvz.hr.hotcoin.local.UserDatabaseHelper
import android.tvz.hr.hotcoin.model.User
import androidx.appcompat.app.AppCompatActivity
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase
    private lateinit var user: User

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // If something exists in the ViewModel constructor, there needs to be a Factory to override the create and take in that value
        val profileViewModel =
            ViewModelProvider(this, ProfileViewModelFactory(requireContext())).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the Room database for the user
        db = UserDatabaseHelper.getInstance(requireContext())
        userDao = db.userDao()
        user = userDao.getUser()

        // Bind the views
        binding.profileUsername.text = user.username
        Picasso.get()
            .load(user.imageUrl)
            .transform(CropCircleTransformation())
            .into(binding.profileImage)

        // Bind the logout button
        val logoutButton = binding.logoutButton
        logoutButton.setOnClickListener{
            // Log the user out and remove it from the Room database
            profileViewModel.logout()
            userDao.deleteUser(user)
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}