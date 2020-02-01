package co.shimm.app.data.model

import android.content.Context
import android.util.Log
import co.shimm.app.data.player.ShimPlayerData
import co.shimm.app.data.retrofit.RetrofitGenerator
import co.shimm.app.data.room.entity.ShimSchedule
import co.shimm.app.data.room.response.ScheduleResponse
import co.shimm.app.data.user.UserInformation
import co.shimm.app.data.user.UserInformation.idToken
import co.shimm.app.view.activity.schedule.ScheduleActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleModel(context : Context) {
    fun initRecyclerViewData(adapter: ScheduleActivity.ScheduleAdapter){
        val scheduleList = ArrayList<ShimSchedule>()

        val scheduleCall = RetrofitGenerator.create().getSchedule("Bearer "+UserInformation.token, ShimPlayerData.shimPlayerCounselor?.id!!)

        scheduleCall.enqueue(object : Callback<ScheduleResponse> {
            override fun onResponse(call: Call<ScheduleResponse>, response: Response<ScheduleResponse>) {
                adapter.setItem(response.body()?.schedules as ArrayList)
                adapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<ScheduleResponse>, t: Throwable) {
                Log.d("Schedule Update Fail", "Schedule Fail")
            }
        })

        adapter.setItem(scheduleList)
    }
}