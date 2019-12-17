package co.shimm.app.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Video::class, Music::class], version = 3, exportSchema = false)
abstract class ShimDatabase : RoomDatabase() {
    abstract val videoDao : VideoDao
    abstract val musicDao : MusicDao

    companion object{
        private var instance: ShimDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ShimDatabase{
            if(instance==null){
                instance = Room.databaseBuilder(context.applicationContext, ShimDatabase::class.java, "shim.db")
                    .fallbackToDestructiveMigration().build()
            }
            return instance as ShimDatabase
        }
    }
}