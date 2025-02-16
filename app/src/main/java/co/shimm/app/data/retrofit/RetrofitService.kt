package co.shimm.app.data.retrofit

import co.shimm.app.data.room.request.AgreeTermsRequest
import co.shimm.app.data.room.response.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("/users/check")
    fun checkUser(@Header("Authorization") googleToken: String?) : Call<CheckUserResponse>

    @POST("/users/auth/")
    fun googleLogin(@Header("Authorization") googleToken : String?) : Call<LoginResponse>

    @POST("/users/terms")
    fun agreeTerms(@Header("Authorization") googleToken : String?, @Body agreeTermsRequest: AgreeTermsRequest) : Call<AgreeTermsResponse>

    @GET("/media/audios")
    fun getAudios(@Header("Authorization") token: String?) : Call<AudioResponse>

    @GET("/media/videos")
    fun getVideos(@Header("Authorization") token: String?) : Call<VideoResponse>

    @GET("/playlists/audios")
    fun getAudioPlaylist(@Header("Authorization") token: String?) : Call<AudioPlaylistResponse>

    @GET("/playlists/videos")
    fun getVideoPlaylist(@Header("Authorization") token: String?) : Call<VideoPlaylistResponse>

    @GET("/counselors")
    fun getCounselorList(@Header("Authorization") token: String?) : Call<CounselorResponse>

    @GET("/playlists/videos/recommends?limit=4")
    fun getRecommendVideoList(@Header("Authorization") token : String?) : Call<VideoPlaylistResponse>

    @GET("/playlists/audios/recommends?limit=4")
    fun getRecommendAudioList(@Header("Authorization") token : String?) : Call<AudioPlaylistResponse>

    @GET("/counselors/{id}/schedules")
    fun getSchedule(@Header("Authorization") token: String?, @Path("id") id: Int) : Call<ScheduleResponse>
}