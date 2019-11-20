package co.shimm.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShimDao {
    @Query("SELECT * FROM shim")
    fun getAll() : List<Shim>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(shimList : List<Shim>)
}