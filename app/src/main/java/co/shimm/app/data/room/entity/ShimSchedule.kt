package co.shimm.app.data.room.entity

import androidx.room.PrimaryKey
import java.sql.Timestamp

class ShimSchedule (@PrimaryKey var id: Int, var counselorId: Int, var userId: Int,
                    var startTime: Timestamp, var endTime: Timestamp)