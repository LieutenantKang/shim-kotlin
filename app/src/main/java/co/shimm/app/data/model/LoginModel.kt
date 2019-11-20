package co.shimm.app.data.model

import android.app.Activity
import android.content.Intent
import android.util.Log
import co.shimm.app.data.room.LoginRequest
import co.shimm.app.data.room.LoginResponse
import co.shimm.app.data.retrofit.RetrofitGenerator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginModel(private val activity: Activity) {
    fun signIn(){
        var gso : GoogleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("758847328465-skprou62c3m1ub7a6ga0mct3v0eeb07s.apps.googleusercontent.com")
            .requestEmail().build()
        var mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)

        var signInIntent : Intent? = mGoogleSignInClient?.signInIntent
        activity.startActivityForResult(signInIntent, 3333)
    }

    fun checkAccount(task: Task<GoogleSignInAccount>){
        try{
            var account : GoogleSignInAccount? = task.getResult(ApiException::class.java)
            var idToken : String? = account?.idToken

            val loginRequest = LoginRequest(idToken!!)
            val call = RetrofitGenerator.create().googleLogin(loginRequest)

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    Log.d("success", response.body()?.arr.toString())
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("fail", "fail")
                }
            })
        }catch(e: ApiException){

        }
    }
}