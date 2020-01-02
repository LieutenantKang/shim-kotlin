package co.shimm.app.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.shimm.app.data.room.entity.ShimAudioPlaylist

@Dao
interface ShimAudioPlaylistDao {
    @Query("SELECT * FROM ShimAudioPlaylist")
    fun getAll() : List<ShimAudioPlaylist>

    @Query("SELECT * FROM ShimAudioPlaylist WHERE category = (:category)")
    fun findByCategory(category: Int) : List<ShimAudioPlaylist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shimAudioPlaylist : List<ShimAudioPlaylist>?)
}