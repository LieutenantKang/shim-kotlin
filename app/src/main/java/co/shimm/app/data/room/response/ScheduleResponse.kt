package co.shimm.app.data.room.response

import co.shimm.app.data.room.entity.ShimSchedule

data class ScheduleResponse (val status: Int, val schedules: List<ShimSchedule>)