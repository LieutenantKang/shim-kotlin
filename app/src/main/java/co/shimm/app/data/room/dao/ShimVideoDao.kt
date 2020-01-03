package co.shimm.app.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.shimm.app.data.room.entity.ShimVideo

@Dao
interface ShimVideoDao {
    @Query("SELECT * FROM ShimVideo WHERE playlistId = (:listId)")
    fun getVideos(listId: Int) : List<ShimVideo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shimAudioPlaylist : List<ShimVideo>?)
}