package android.tvz.hr.hotcoin.ui.profile

import android.content.Context
import android.content.Intent
import android.tvz.hr.hotcoin.LoginActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel(private val context: Context) : ViewModel() {


    fun logout(){
        val sharedPreferences = context.getSharedPreferences("loginStatus", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }
}