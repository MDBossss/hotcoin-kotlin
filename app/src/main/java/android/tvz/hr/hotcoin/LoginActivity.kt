package android.tvz.hr.hotcoin

import android.content.Intent
import android.os.Bundle
import android.tvz.hr.hotcoin.api.LoginService
import android.tvz.hr.hotcoin.databinding.ActivityLoginBinding
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

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginService: LoginService
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Room database for the user
        db = UserDatabaseHelper.getInstance(this)
        userDao = db.userDao()

        // Initialize LoginService
        loginService = RetrofitHelper().createService(LoginService::class.java,Constants.DATABASE_URL)

        binding.loginButton.setOnClickListener {
            attemptLogin()
        }
    }

    private fun attemptLogin() {
        val username = binding.usernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        val loginRequest = LoginRequest(username,password)

        loginService.login(loginRequest).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val loginResponse = response.body()
                    if(loginResponse != null){
                        // Store the user with Room database
                        userDao.insertUser(loginResponse)

                        // Remember the user is logged in
                        val sharedPreferences = getSharedPreferences("loginStatus", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isLoggedIn", true)
                        editor.apply()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }else{
                        Toast.makeText(this@LoginActivity, "Failed to get the logged in user", Toast.LENGTH_SHORT).show()
                    }

                }
                else{
                    Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
