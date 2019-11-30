package co.shimm.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShimDao {
    @Query("SELECT * FROM shim")
    fun getAll() : List<Shim>

    @Query("SELECT * FROM shim WHERE category = (:category)")
    fun findByCategory(category: Int) : List<Shim>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shimList: ArrayList<Shim>?)
}