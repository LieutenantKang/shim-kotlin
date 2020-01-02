package co.shimm.app.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.shimm.app.data.room.entity.ShimAudio

@Dao
interface ShimAudioDao {
    @Query("SELECT * FROM ShimAudio WHERE playlistId = (:listId)")
    fun getAudios(listId: Int) : List<ShimAudio>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shimAudioPlaylist : List<ShimAudio>?)
}