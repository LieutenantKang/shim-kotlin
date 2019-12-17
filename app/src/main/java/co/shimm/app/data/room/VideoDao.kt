package co.shimm.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao {
    @Query("SELECT * FROM video")
    fun getAll() : List<Video>

    @Query("SELECT * FROM video WHERE category = (:category)")
    fun findByCategory(category: Int) : List<Video>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(videoList: ArrayList<Video>?)
}