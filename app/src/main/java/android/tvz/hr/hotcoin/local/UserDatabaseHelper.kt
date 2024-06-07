package android.tvz.hr.hotcoin.local

import android.content.Context
import androidx.room.Room

object UserDatabaseHelper {
    private var db: UserDatabase? = null

    fun getInstance(context:Context): UserDatabase{
        if(db == null){
            db = Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,"user"
            ).allowMainThreadQueries().build()
        }
        return db!!
    }
}