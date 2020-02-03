package co.shimm.app.data.model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import co.shimm.app.BuildConfig
import co.shimm.app.data.user.UserInformation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class SettingModel(private val activity : Activity) {
    var sharedPreferences: SharedPreferences = activity.applicationContext.getSharedPreferences("pref", 0)

    fun logout(){
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()

        val gso: GoogleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.CLIENT_ID)
            .requestEmail().build()
        val mGoogleApiClient = GoogleSignIn.getClient(activity, gso)

        mGoogleApiClient.signOut()

        UserInformation.token = ""
    }
}