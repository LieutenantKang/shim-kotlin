package co.shimm.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MusicDao {
    @Query("SELECT * FROM music")
    fun getAll() : List<Music>

    @Query("SELECT * FROM music WHERE category = (:category)")
    fun findByCategory(category: String) : List<Music>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(musicList : List<Music>)
}