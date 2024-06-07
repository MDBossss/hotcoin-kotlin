package android.tvz.hr.hotcoin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class LoginRequest(
    val username: String,
    val password: String
)

@Entity
data class User(
    @PrimaryKey
    val id: String,
    val username: String,
    val imageUrl: String
)