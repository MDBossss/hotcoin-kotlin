package android.tvz.hr.hotcoin.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class LoginRequest(
    val username: String,
    val password: String
)

@Entity
@Parcelize
data class User(
    @PrimaryKey
    val id: String,
    val username: String,
    val imageUrl: String
) : Parcelable