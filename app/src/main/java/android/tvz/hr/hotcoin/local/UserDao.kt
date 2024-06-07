package android.tvz.hr.hotcoin.local

import android.tvz.hr.hotcoin.model.User
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id = '1'")
    fun getUser(): User

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)
}