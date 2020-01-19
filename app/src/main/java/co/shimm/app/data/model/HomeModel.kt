package co.shimm.app.data.model

import android.content.Context
import android.util.Log
import co.shimm.app.data.retrofit.RetrofitGenerator
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.data.room.entity.ShimVideo
import co.shimm.app.data.room.response.AudioResponse
import co.shimm.app.data.room.response.VideoResponse
import co.shimm.app.data.user.UserInformation
import co.shimm.app.view.fragment.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeModel(context: Context) {
    fun initRecyclerViewData(videoAdapter: HomeFragment.HomeVideoAdapter, audioAdapter: HomeFragment.HomeAudioAdapter){
        val shimVideoList = ArrayList<ShimVideo>()
        val shimAudioList = ArrayList<ShimAudio>()

        val shimVideoCall = RetrofitGenerator.create().getRecommendVideoList("Bearer "+ UserInformation.token)
        shimVideoCall.enqueue((object: Callback<VideoResponse> {
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                videoAdapter.setItem(response.body()?.videos as ArrayList)
                videoAdapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                Log.d("Home Video Fail","Home Video Fail")
            }
        }))

        val shimAudioCall = RetrofitGenerator.create().getRecommendAudioList("Bearer " + UserInformation.token)
        shimAudioCall.enqueue((object: Callback<AudioResponse> {
            override fun onResponse(call: Call<AudioResponse>, response: Response<AudioResponse>) {
                audioAdapter.setItem(response.body()?.audios as ArrayList)
                audioAdapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<AudioResponse>, t: Throwable) {
                Log.d("Home Audio Fail","Home Audio Fail")
            }
        }))

        videoAdapter.setItem(shimVideoList)
        audioAdapter.setItem(shimAudioList)
    }
}