package co.shimm.app.data.model

import android.content.Context
import android.util.Log
import co.shimm.app.data.retrofit.RetrofitGenerator
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.data.room.entity.ShimAudioPlaylist
import co.shimm.app.data.room.entity.ShimVideo
import co.shimm.app.data.room.entity.ShimVideoPlaylist
import co.shimm.app.data.room.response.AudioPlaylistResponse
import co.shimm.app.data.room.response.AudioResponse
import co.shimm.app.data.room.response.VideoPlaylistResponse
import co.shimm.app.data.room.response.VideoResponse
import co.shimm.app.data.user.UserInformation
import co.shimm.app.view.fragment.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeModel(context: Context) {
    fun initRecyclerViewData(videoAdapter: HomeFragment.HomeVideoAdapter, audioAdapter: HomeFragment.HomeAudioAdapter){
        val shimVideoList = ArrayList<ShimVideoPlaylist>()
        val shimAudioList = ArrayList<ShimAudioPlaylist>()

        val shimVideoCall = RetrofitGenerator.create().getRecommendVideoList("Bearer "+ UserInformation.token)
        shimVideoCall.enqueue((object: Callback<VideoPlaylistResponse> {
            override fun onResponse(call: Call<VideoPlaylistResponse>, response: Response<VideoPlaylistResponse>) {
                videoAdapter.setItem(response.body()?.videoPlaylists as ArrayList)
                videoAdapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<VideoPlaylistResponse>, t: Throwable) {
                Log.d("Home Video Fail","Home Video Fail")
            }
        }))

        val shimAudioCall = RetrofitGenerator.create().getRecommendAudioList("Bearer " + UserInformation.token)
        shimAudioCall.enqueue((object: Callback<AudioPlaylistResponse> {
            override fun onResponse(call: Call<AudioPlaylistResponse>, response: Response<AudioPlaylistResponse>) {
                audioAdapter.setItem(response.body()?.audioPlaylists as ArrayList)
                audioAdapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<AudioPlaylistResponse>, t: Throwable) {
                Log.d("Home Audio Fail","Home Audio Fail")
            }
        }))

        videoAdapter.setItem(shimVideoList)
        audioAdapter.setItem(shimAudioList)
    }
}