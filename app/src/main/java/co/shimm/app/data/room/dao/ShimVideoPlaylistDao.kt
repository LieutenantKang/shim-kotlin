package co.shimm.app.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.shimm.app.data.room.entity.ShimVideoPlaylist

@Dao
interface ShimVideoPlaylistDao {
    @Query("SELECT * FROM ShimVideoPlaylist")
    fun getAll() : List<ShimVideoPlaylist>

    @Query("SELECT * FROM ShimVideoPlaylist WHERE category = (:category)")
    fun findByCategory(category: Int) : List<ShimVideoPlaylist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shimVideoPlaylist: List<ShimVideoPlaylist>?)
}