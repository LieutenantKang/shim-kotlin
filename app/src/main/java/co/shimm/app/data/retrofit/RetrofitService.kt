package co.shimm.app.data.retrofit

import co.shimm.app.data.room.LoginResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitService {
    @POST("/users/auth/")
    fun googleLogin(@Header("Authorization") googleToken : String?) : Call<LoginResponse>
}