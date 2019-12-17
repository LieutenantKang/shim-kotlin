package co.shimm.app.data.retrofit

import co.shimm.app.data.room.response.LoginResponse
import co.shimm.app.data.room.response.MusicResponse
import co.shimm.app.data.room.response.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitService {
    @POST("/users/auth/")
    fun googleLogin(@Header("Authorization") googleToken : String?) : Call<LoginResponse>

    @GET("/musics/")
    fun getMusics() : Call<MusicResponse>

    @GET("/videos/")
    fun getShims() : Call<VideoResponse>
}