package co.shimm.app.view.activity.login

import android.app.Activity
import co.shimm.app.data.model.LoginModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

class LoginPresenter(private val view: LoginContract.View, activity: Activity) : LoginContract.Presenter, LoginContract.Model.LoginFinishedListener {
    private val loginModel: LoginModel = LoginModel(activity)

    override fun start() {
        view.presenter = this
    }

    override fun signIn() {
        loginModel.signIn()
    }

    override fun checkAccount(task: Task<GoogleSignInAccount>) {
        loginModel.checkAccount(task, this)
    }

    override fun onFinished(googleToken: String) {
        view.startMainActivity()
    }

    override fun onFailure(t: Throwable) { }
}