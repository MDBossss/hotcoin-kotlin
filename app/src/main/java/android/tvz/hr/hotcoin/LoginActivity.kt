package android.tvz.hr.hotcoin

import android.content.Intent
import android.os.Bundle
import android.tvz.hr.hotcoin.databinding.ActivityLoginBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.loginButton.setOnClickListener {
            attemptLogin()
        }
    }

    private fun attemptLogin() {
        val username = binding.usernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        // Replace with your actual login logic and data
        if (username == "test" && password == "test") {
            // Login successful, start MainActivity
            val sharedPreferences = getSharedPreferences("loginStatus", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", true)
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close LoginActivity after successful login
        } else {
            // Login failed, show error message
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }
}
