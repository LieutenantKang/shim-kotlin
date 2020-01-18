package co.shimm.app.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.shimm.app.data.room.dao.*
import co.shimm.app.data.room.entity.*

@Database(entities = [ShimVideoPlaylist::class, ShimAudioPlaylist::class, ShimVideo::class, ShimAudio::class, ShimCounselor::class], version = 10, exportSchema = false)
abstract class ShimDatabase : RoomDatabase() {
    abstract val shimVideoPlaylistDao : ShimVideoPlaylistDao
    abstract val shimAudioPlaylistDao : ShimAudioPlaylistDao
    abstract val shimVideoDao : ShimVideoDao
    abstract val shimAudioDao : ShimAudioDao
    abstract val shimCounselorDao : ShimCounselorDao

    companion object{
        private var instance: ShimDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ShimDatabase {
            if(instance ==null){
                instance = Room.databaseBuilder(context.applicationContext, ShimDatabase::class.java, "shim.db")
                    .fallbackToDestructiveMigration().build()
            }
            return instance as ShimDatabase
        }
    }
}