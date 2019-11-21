package co.shimm.app.data.model

import android.content.Context
import co.shimm.app.data.room.Music
import co.shimm.app.data.room.MusicDao
import co.shimm.app.data.room.ShimDao
import co.shimm.app.data.room.ShimDatabase

class MainModel(context: Context) {
    private var database: ShimDatabase = ShimDatabase.getInstance(context)
    private var shimDao: ShimDao
    private var musicDao: MusicDao

    init {
        shimDao = database.shimDao
        musicDao = database.musicDao
    }

    fun fetchData(){
        val musicList = ArrayList<Music>()
        musicList.add(Music(1, "favorite-1", "favorite"))
        musicList.add(Music(2, "favorite-2", "favorite"))
        musicList.add(Music(3, "favorite-3", "favorite"))
        musicList.add(Music(4, "asmr-1", "asmr"))
        musicList.add(Music(5, "asmr-2", "asmr"))
        musicList.add(Music(6, "relax-1", "relax"))
        musicList.add(Music(7, "relax-2", "relax"))
        musicList.add(Music(8 , "relax-3", "relax"))
        musicList.add(Music(9, "relax-4", "relax"))
        musicList.add(Music(10, "focus-1", "focus"))
        musicList.add(Music(11, "focus-2", "focus"))
        musicList.add(Music(12,"focus-3", "focus"))

        Thread { database.musicDao.insertAll(musicList)}.start()
    }
}