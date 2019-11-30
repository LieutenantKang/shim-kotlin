package co.shimm.app.data.model

import android.content.Context
import android.util.Log
import co.shimm.app.data.retrofit.RetrofitGenerator
import co.shimm.app.data.room.MusicDao
import co.shimm.app.data.room.ShimDao
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.data.room.response.MusicResponse
import co.shimm.app.data.room.response.ShimResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel(context: Context) {
    private var database: ShimDatabase = ShimDatabase.getInstance(context)
    private var shimDao: ShimDao
    private var musicDao: MusicDao

    init {
        shimDao = database.shimDao
        musicDao = database.musicDao
    }

    fun fetchData(){
        val shimCall = RetrofitGenerator.create().getShims()
        shimCall.enqueue(object : Callback<ShimResponse>{
            override fun onResponse(call: Call<ShimResponse>, response: Response<ShimResponse>) {
                for(i in response.body()?.shims!!){
                    i.src = "http://s3.ap-northeast-2.amazonaws.com/shim-music/" + i.src
                    i.thumbnail = "http://s3.ap-northeast-2.amazonaws.com/shim-music/" + i.thumbnail
                }
                Thread { shimDao.insert(response.body()?.shims)}.start()
            }
            override fun onFailure(call: Call<ShimResponse>, t: Throwable) {
                Log.d("fail","fail")
            }
        })

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