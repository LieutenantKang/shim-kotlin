package co.shimm.app.data.model

import android.content.Context
import android.net.Uri
import android.util.Log
import co.shimm.app.R
import co.shimm.app.data.player.ShimPlayerData
import co.shimm.app.data.retrofit.RetrofitGenerator
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.data.room.dao.*
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.data.room.response.*
import co.shimm.app.data.user.UserInformation
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel(context: Context) {
    private var database: ShimDatabase = ShimDatabase.getInstance(context)
    private var shimVideoPlaylistDao: ShimVideoPlaylistDao
    private var shimAudioPlaylistDao: ShimAudioPlaylistDao
    private var shimVideoDao: ShimVideoDao
    private var shimAudioDao: ShimAudioDao
    private var shimCounselorDao: ShimCounselorDao

    init {
        shimVideoPlaylistDao = database.shimVideoPlaylistDao
        shimAudioPlaylistDao = database.shimAudioPlaylistDao
        shimVideoDao = database.shimVideoDao
        shimAudioDao = database.shimAudioDao
        shimCounselorDao = database.shimCounselorDao
    }

    fun fetchData(){
        val videoCall = RetrofitGenerator.create().getVideos("Bearer "+UserInformation.token)
        videoCall.enqueue((object: Callback<VideoResponse>{
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                Thread{shimVideoDao.insert(response.body()?.videos)}.start()
            }
            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                Log.d("videoFail","videoFail")
            }
        }))

        val audioCall = RetrofitGenerator.create().getAudios("Bearer "+UserInformation.token)
        audioCall.enqueue((object: Callback<AudioResponse>{
            override fun onResponse(call: Call<AudioResponse>, response: Response<AudioResponse>) {
                Thread{shimAudioDao.insert(response.body()?.audios)}.start()
            }
            override fun onFailure(call: Call<AudioResponse>, t: Throwable) {
                Log.d("audioFail","audioFail")
            }
        }))

        val videoPlaylistCall = RetrofitGenerator.create().getVideoPlaylist("Bearer "+UserInformation.token)
        videoPlaylistCall.enqueue((object: Callback<VideoPlaylistResponse>{
            override fun onResponse(call: Call<VideoPlaylistResponse>, response: Response<VideoPlaylistResponse>) {
                Thread{shimVideoPlaylistDao.insert(response.body()?.videoPlaylists)}.start()
            }
            override fun onFailure(call: Call<VideoPlaylistResponse>, t: Throwable) {
                Log.d("videoPlaylistFail","videoPlaylistFail")
            }
        }))

        val audioPlaylistCall = RetrofitGenerator.create().getAudioPlaylist("Bearer "+UserInformation.token)
        audioPlaylistCall.enqueue((object: Callback<AudioPlaylistResponse>{
            override fun onResponse(call: Call<AudioPlaylistResponse>, response: Response<AudioPlaylistResponse>) {
                Thread{shimAudioPlaylistDao.insert(response.body()?.audioPlaylists)}.start()
            }
            override fun onFailure(call: Call<AudioPlaylistResponse>, t: Throwable) {
                Log.d("audioPlaylistFail","audioPlaylistFail")
            }
        }))

        val counselorCall = RetrofitGenerator.create().getCounselorList("Bearer "+UserInformation.token)
        counselorCall.enqueue((object: Callback<CounselorResponse>{
            override fun onResponse(call: Call<CounselorResponse>, response: Response<CounselorResponse>) {
                Thread{shimCounselorDao.insert(response.body()?.counselors)}.start()
            }
            override fun onFailure(call: Call<CounselorResponse>, t: Throwable) {
                Log.d("counselorFail","counselorFail")
            }
        }))
    }

    fun playNext(){
        Log.d("BEFORE INDEX", ShimPlayerData.shimPlayIndex.toString())
        ShimPlayerData.shimPlayIndex = ShimPlayerData.shimPlayIndex?.plus(1)
        if(ShimPlayerData.shimPlayIndex == ShimPlayerData.shimPlaylist?.size){
            ShimPlayerData.shimPlayIndex = 0
        }
        Log.d("PlayNEXTINDEX", ShimPlayerData.shimPlayIndex.toString())
        val shimAudio = ShimPlayerData.shimPlaylist?.get(ShimPlayerData.shimPlayIndex!!)
        playAudio(shimAudio)
    }

    fun playPrevious(){
        ShimPlayerData.shimPlayIndex = ShimPlayerData.shimPlayIndex?.minus(1)
        if(ShimPlayerData.shimPlayIndex == -1){
            ShimPlayerData.shimPlayIndex = ShimPlayerData.shimPlaylist?.size?.minus(1)
        }

        val shimAudio = ShimPlayerData.shimPlaylist?.get(ShimPlayerData.shimPlayIndex!!)
        playAudio(shimAudio)
    }

    private fun playAudio(shimAudio: ShimAudio?){
        val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(R.string.app_name.toString()))
            .createMediaSource(Uri.parse(shimAudio?.src))
        ShimPlayerData.shimPlayer?.prepare(mediaSource)
        ShimPlayerData.shimPlayer?.playWhenReady = true
        ShimPlayerData.shimPlayerTitle = shimAudio?.title
    }
}