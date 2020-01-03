package co.shimm.app.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.shimm.app.data.room.dao.ShimAudioDao
import co.shimm.app.data.room.dao.ShimAudioPlaylistDao
import co.shimm.app.data.room.dao.ShimVideoDao
import co.shimm.app.data.room.dao.ShimVideoPlaylistDao
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.data.room.entity.ShimAudioPlaylist
import co.shimm.app.data.room.entity.ShimVideo
import co.shimm.app.data.room.entity.ShimVideoPlaylist

@Database(entities = [ShimVideoPlaylist::class, ShimAudioPlaylist::class, ShimVideo::class, ShimAudio::class], version = 7, exportSchema = false)
abstract class ShimDatabase : RoomDatabase() {
    abstract val shimVideoPlaylistDao : ShimVideoPlaylistDao
    abstract val shimAudioPlaylistDao : ShimAudioPlaylistDao
    abstract val shimVideoDao : ShimVideoDao
    abstract val shimAudioDao : ShimAudioDao

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