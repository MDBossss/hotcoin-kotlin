package android.tvz.hr.hotcoin.local

import android.tvz.hr.hotcoin.model.User
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}