package co.shimm.app.data.model

import android.app.Activity
import android.content.Intent
import android.util.Log
import co.shimm.app.const.Const.Login.REQUEST_CODE
import co.shimm.app.data.user.UserInformation
import co.shimm.app.data.retrofit.RetrofitGenerator
import co.shimm.app.data.room.response.CheckUserResponse
import co.shimm.app.data.room.response.LoginResponse
import co.shimm.app.view.activity.login.LoginContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginModel(private val activity: Activity) : LoginContract.Model {
    override fun signIn(){
        val gso : GoogleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("758847328465-skprou62c3m1ub7a6ga0mct3v0eeb07s.apps.googleusercontent.com")
            .requestEmail().build()
        val mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)

        val signInIntent : Intent? = mGoogleSignInClient?.signInIntent
        activity.startActivityForResult(signInIntent, REQUEST_CODE)
    }

    override fun checkAccount(task: Task<GoogleSignInAccount>, checkAccountFinishedListener: LoginContract.Model.CheckAccountFinishedListener){
        try{
            val account : GoogleSignInAccount? = task.getResult(ApiException::class.java)
            val idToken : String? = account?.idToken

            val call = RetrofitGenerator.create().checkUser("Bearer $idToken")

            call.enqueue(object : Callback<CheckUserResponse> {
                override fun onResponse(call: Call<CheckUserResponse>, response: Response<CheckUserResponse>) {
                    Log.d("User Check Success", response.body()?.check.toString())
                    if(response.body()?.check!!){
                        checkAccountFinishedListener.idExist(idToken)
                    }else{
                        checkAccountFinishedListener.idAbsence(idToken)
                    }
                }
                override fun onFailure(call: Call<CheckUserResponse>, t: Throwable) {
                    Log.d("User Check Fail", "User Check Fail")
                }
            })
        }catch(e: ApiException){

        }
    }

    override fun googleLogin(idToken: String?, loginFinishedListener: LoginContract.Model.LoginFinishedListener) {
        try{
            val call = RetrofitGenerator.create().googleLogin("Bearer $idToken")

            call.enqueue(object : Callback<LoginResponse> {
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
        }catch(e: ApiException){

        }
    }
}