package android.tvz.hr.hotcoin.api

import android.tvz.hr.hotcoin.model.LoginRequest
import android.tvz.hr.hotcoin.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<User>

    @POST("/api/auth/register")
    fun register(@Body registerRequest: LoginRequest): Call<User>
}