package android.tvz.hr.hotcoin

import android.content.Intent
import android.os.Bundle
import android.tvz.hr.hotcoin.api.LoginService
import android.tvz.hr.hotcoin.databinding.ActivityLoginBinding
import android.tvz.hr.hotcoin.databinding.ActivityRegisterBinding
import android.tvz.hr.hotcoin.local.UserDao
import android.tvz.hr.hotcoin.local.UserDatabase
import android.tvz.hr.hotcoin.local.UserDatabaseHelper
import android.tvz.hr.hotcoin.model.LoginRequest
import android.tvz.hr.hotcoin.model.User
import android.tvz.hr.hotcoin.util.Constants
import android.tvz.hr.hotcoin.util.RetrofitHelper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var loginService: LoginService
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Room database for the user
        db = UserDatabaseHelper.getInstance(this)
        userDao = db.userDao()

        // Initialize LoginService
        loginService = RetrofitHelper().createService(LoginService::class.java,Constants.DATABASE_URL)

        // Bind register button
        binding.registerButton.setOnClickListener {
            attemptRegister()
        }

        // Bind go to login text
        binding.gotoLogin.setOnClickListener{
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun attemptRegister() {
        val username = binding.usernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        val loginRequest = LoginRequest(username,password)

        loginService.register(loginRequest).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    // User is registered, redirect to login
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                    Toast.makeText(this@RegisterActivity, "User registered, login please", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@RegisterActivity, "Failed to register user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
