package co.shimm.app.data.model

import android.content.Context
import android.util.Log
import co.shimm.app.data.retrofit.RetrofitGenerator
import co.shimm.app.data.room.dao.ShimAudioPlaylistDao
import co.shimm.app.data.room.dao.ShimVideoPlaylistDao
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.data.room.dao.ShimAudioDao
import co.shimm.app.data.room.dao.ShimVideoDao
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.data.room.entity.ShimAudioPlaylist
import co.shimm.app.data.room.entity.ShimVideo
import co.shimm.app.data.room.entity.ShimVideoPlaylist
import co.shimm.app.data.room.response.AudioPlaylistResponse
import co.shimm.app.data.room.response.AudioResponse
import co.shimm.app.data.room.response.VideoPlaylistResponse
import co.shimm.app.data.room.response.VideoResponse
import co.shimm.app.data.user.UserInformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel(context: Context) {
    private var database: ShimDatabase = ShimDatabase.getInstance(context)
    private var shimVideoPlaylistDao: ShimVideoPlaylistDao
    private var shimAudioPlaylistDao: ShimAudioPlaylistDao
    private var shimVideoDao: ShimVideoDao
    private var shimAudioDao: ShimAudioDao

    init {
        shimVideoPlaylistDao = database.shimVideoPlaylistDao
        shimAudioPlaylistDao = database.shimAudioPlaylistDao
        shimVideoDao = database.shimVideoDao
        shimAudioDao = database.shimAudioDao
    }

    fun fetchData(){

        val videoList = arrayListOf<ShimVideo>()
        videoList.add(ShimVideo(0, 0, "Video1", "Video1", "http://s3.ap-northeast-2.amazonaws.com/shim-music/14.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            100000, "", "",""))
        videoList.add(ShimVideo(1, 0, "Video2", "Video2", "http://s3.ap-northeast-2.amazonaws.com/shim-music/13.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            100000, "","",""))
        videoList.add(ShimVideo(2, 1, "Video3", "Video3", "http://s3.ap-northeast-2.amazonaws.com/shim-music/12.jpg",
            "http://http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            100000, "","",""))
        videoList.add(ShimVideo(3, 1, "Video4", "Video4", "http://s3.ap-northeast-2.amazonaws.com/shim-music/11.jpg",
            "http://http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            100000, "","",""))
        Thread { shimVideoDao.insert(videoList)}.start()

        val videoPlaylist = arrayListOf<ShimVideoPlaylist>()
        videoPlaylist.add(ShimVideoPlaylist(0,0,"VideoPlaylist1",1,"VideoPlaylist1 Description",
            "http://s3.ap-northeast-2.amazonaws.com/shim-music/14.jpg", 1, "", "", ""))
        videoPlaylist.add(ShimVideoPlaylist(1,0,"VideoPlaylist2",2,"VideoPlaylist2 Description",
            "http://s3.ap-northeast-2.amazonaws.com/shim-music/12.jpg", 1, "", "", ""))
        Thread { shimVideoPlaylistDao.insert(videoPlaylist)}.start()

        val audioList = arrayListOf<ShimAudio>()
        audioList.add(ShimAudio(0, 0, "Audio1", "Audio1", "http://s3.ap-northeast-2.amazonaws.com/shim-music/11.jpg",
            "https://s3.ap-northeast-2.amazonaws.com/shim-music/White_River.mp3", 100000, "","",""))
        audioList.add(ShimAudio(1, 0, "Audio2", "Audio2", "http://s3.ap-northeast-2.amazonaws.com/shim-music/12.jpg",
            "https://s3.ap-northeast-2.amazonaws.com/shim-music/A_Quiet_Thought.mp3", 100000, "","",""))
        audioList.add(ShimAudio(2, 1, "Audio3", "Audio3", "http://s3.ap-northeast-2.amazonaws.com/shim-music/13.jpg",
            "https://s3.ap-northeast-2.amazonaws.com/shim-music/Angels_Dream.mp3", 100000, "","",""))
        audioList.add(ShimAudio(3, 1, "Audio4", "Audio4", "http://s3.ap-northeast-2.amazonaws.com/shim-music/14.jpg",
            "https://s3.ap-northeast-2.amazonaws.com/shim-music/A_Quiet_Thought.mp3", 100000, "","",""))
        Thread { shimAudioDao.insert(audioList)}.start()

        val audioPlaylist = arrayListOf<ShimAudioPlaylist>()
        audioPlaylist.add(ShimAudioPlaylist(0,0,"AudioPlaylist1",1,"AudioPlaylist1 Description",
            "http://s3.ap-northeast-2.amazonaws.com/shim-music/11.jpg", 1, "", "", ""))
        audioPlaylist.add(ShimAudioPlaylist(1,0,"AudioPlaylist",2,"AudioPlaylist2 Description",
            "http://s3.ap-northeast-2.amazonaws.com/shim-music/13.jpg", 1, "", "", ""))
        Thread { shimAudioPlaylistDao.insert(audioPlaylist)}.start()

//        val videoCall = RetrofitGenerator.create().getVideos("Bearer "+UserInformation.token)
//        videoCall.enqueue((object: Callback<VideoResponse>{
//            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
//                for(i in response.body()?.videos!!){
//                    i.thumbnail = "https://shim-music.s3.ap-northeast-2.amazonaws.com/" + i.thumbnail
//                    i.src = "https://shim-music.s3.ap-northeast-2.amazonaws.com/" + i.src
//                    Thread{shimVideoDao.insert(response.body()?.videos)}.start()
//                }
//            }
//            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
//                Log.d("videoFail","videoFail")
//            }
//        }))
//
//        val audioCall = RetrofitGenerator.create().getAudios("Bearer "+UserInformation.token)
//        audioCall.enqueue((object: Callback<AudioResponse>{
//            override fun onResponse(call: Call<AudioResponse>, response: Response<AudioResponse>) {
//                for(i in response.body()?.audios!!){
//                    i.thumbnail = "https://shim-music.s3.ap-northeast-2.amazonaws.com/" + i.thumbnail
//                    i.src = "https://shim-music.s3.ap-northeast-2.amazonaws.com/" + i.src
//                    Thread{shimAudioDao.insert(response.body()?.audios)}.start()
//                }
//            }
//            override fun onFailure(call: Call<AudioResponse>, t: Throwable) {
//                Log.d("audioFail","audioFail")
//            }
//        }))
//
//        val videoPlaylistCall = RetrofitGenerator.create().getVideoPlaylist("Bearer "+UserInformation.token)
//        videoPlaylistCall.enqueue((object: Callback<VideoPlaylistResponse>{
//            override fun onResponse(call: Call<VideoPlaylistResponse>, response: Response<VideoPlaylistResponse>) {
//                for(i in response.body()?.videoPlaylists!!){
//                    i.thumbnail = "https://shim-music.s3.ap-northeast-2.amazonaws.com/" + i.thumbnail
//                    Thread{shimVideoPlaylistDao.insert(response.body()?.videoPlaylists)}.start()
//                }
//            }
//            override fun onFailure(call: Call<VideoPlaylistResponse>, t: Throwable) {
//                Log.d("videoPlaylistFail","videoPlaylistFail")
//            }
//        }))
//
//        val audioPlaylistCall = RetrofitGenerator.create().getAudioPlaylist("Bearer "+UserInformation.token)
//        audioPlaylistCall.enqueue((object: Callback<AudioPlaylistResponse>{
//            override fun onResponse(call: Call<AudioPlaylistResponse>, response: Response<AudioPlaylistResponse>) {
//                for(i in response.body()?.audioPlaylists!!){
//                    i.thumbnail = "https://shim-music.s3.ap-northeast-2.amazonaws.com/" + i.thumbnail
//                    Thread{shimAudioPlaylistDao.insert(response.body()?.audioPlaylists)}.start()
//                }
//            }
//            override fun onFailure(call: Call<AudioPlaylistResponse>, t: Throwable) {
//                Log.d("audioPlaylistFail","audioPlaylistFail")
//            }
//        }))


    }
}