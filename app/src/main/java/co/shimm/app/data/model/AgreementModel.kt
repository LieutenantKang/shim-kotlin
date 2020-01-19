package co.shimm.app.data.model

import android.util.Log
import co.shimm.app.data.retrofit.RetrofitGenerator
import co.shimm.app.data.room.request.AgreeTermsRequest
import co.shimm.app.data.room.response.AgreeTermsResponse
import co.shimm.app.data.room.response.LoginResponse
import co.shimm.app.data.user.UserInformation
import co.shimm.app.view.activity.agreement.AgreementContract
import com.google.android.gms.common.api.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgreementModel: AgreementContract.Model {
    override fun googleLogin(idToken: String?, loginFinishedListener: AgreementContract.Model.LoginFinishedListener) {
        try{
            val loginCall = RetrofitGenerator.create().googleLogin("Bearer $idToken")

            loginCall.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    Log.d("success", response.body()?.token.toString())
                    UserInformation.token = response.body()?.token.toString()
                    loginFinishedListener.onFinished(response.body()?.token.toString())
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("fail", "fail")
                    loginFinishedListener.onFailure(t)
                }
            })
        }catch(e: ApiException){ }
    }

    override fun agreeTerms(agreementFinishedListener: AgreementContract.Model.AgreementFinishedListener) {
        try{
            val agreeCall = RetrofitGenerator.create().
                agreeTerms("Bearer " + UserInformation.token, AgreeTermsRequest(true,true,true))

            agreeCall.enqueue(object : Callback<AgreeTermsResponse>{
                override fun onResponse(call: Call<AgreeTermsResponse>, response: Response<AgreeTermsResponse>) {
                    Log.d("Agree Terms Success", response.body()?.message.toString())
                    agreementFinishedListener.agreeFinished()
                }
                override fun onFailure(call: Call<AgreeTermsResponse>, t: Throwable) {
                    Log.d("Agree Terms Fail", "fail")
                    agreementFinishedListener.agreeFailed()
                }
            })
        }catch(e: ApiException){

        }
    }
}