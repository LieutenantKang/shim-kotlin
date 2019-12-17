package co.shimm.app.data.model

import android.content.Context
import android.util.Log
import co.shimm.app.data.retrofit.RetrofitGenerator
import co.shimm.app.data.room.MusicDao
import co.shimm.app.data.room.VideoDao
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.data.room.Video
import co.shimm.app.data.room.response.MusicResponse
import co.shimm.app.data.room.response.VideoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel(context: Context) {
    private var database: ShimDatabase = ShimDatabase.getInstance(context)
    private var videoDao: VideoDao
    private var musicDao: MusicDao

    init {
        videoDao = database.videoDao
        musicDao = database.musicDao
    }

    fun fetchData(){
//        val shimCall = RetrofitGenerator.create().getShims()
//        shimCall.enqueue(object : Callback<VideoResponse>{
//            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
//                for(i in response.body()?.videos!!){
//                    i.src = "http://s3.ap-northeast-2.amazonaws.com/shim-music/" + i.src
//                    i.thumbnail = "http://s3.ap-northeast-2.amazonaws.com/shim-music/" + i.thumbnail
//                }
//                Thread { videoDao.insert(response.body()?.videos)}.start()
//            }
//            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
//                Log.d("fail","fail")
//            }
//        })

        val videoList = arrayListOf<Video>()
        videoList.add(Video(0, "Video1", 0, "Video1", "http://techslides.com/demos/sample-videos/small.mp4",
            "http://s3.ap-northeast-2.amazonaws.com/shim-music/14.jpg", 100000))
        videoList.add(Video(1, "Video2", 0, "Video2", "http://techslides.com/demos/sample-videos/small.mp4",
            "http://s3.ap-northeast-2.amazonaws.com/shim-music/13.jpg", 100000))
        videoList.add(Video(2, "Video3", 0, "Video3", "http://techslides.com/demos/sample-videos/small.mp4",
            "http://s3.ap-northeast-2.amazonaws.com/shim-music/12.jpg", 100000))
        Thread { videoDao.insert(videoList)}.start()

        val musicCall = RetrofitGenerator.create().getMusics()
        musicCall.enqueue(object : Callback<MusicResponse>{
            override fun onResponse(call: Call<MusicResponse>, response: Response<MusicResponse>) {
                for(i in response.body()?.musics!!){
                    i.src = "http://s3.ap-northeast-2.amazonaws.com/shim-music/" + i.src
                    i.thumbnail = "http://s3.ap-northeast-2.amazonaws.com/shim-music/" + i.thumbnail
                }
                Thread { musicDao.insertAll(response.body()?.musics)}.start()
            }
            override fun onFailure(call: Call<MusicResponse>, t: Throwable) {
                Log.d("fail","fail")
            }
        })
    }
}