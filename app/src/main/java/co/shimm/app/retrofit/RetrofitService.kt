package co.shimm.app.retrofit

import co.shimm.app.data.room.LoginRequest
import co.shimm.app.data.room.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("/login/")
    fun googleLogin(@Body loginRequest : LoginRequest) : Call<LoginResponse>
}