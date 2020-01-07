package co.shimm.app.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.shimm.app.data.room.entity.ShimCounselor


@Dao
interface ShimCounselorDao {
    @Query("SELECT * FROM ShimCounselor WHERE id = (:counselorId)")
    fun getCounselor(counselorId: Int) : List<ShimCounselor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shimCounselorList : List<ShimCounselor>?)
}