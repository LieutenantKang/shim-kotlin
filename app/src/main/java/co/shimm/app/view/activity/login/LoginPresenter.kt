package co.shimm.app.view.activity.login

import android.app.Activity
import co.shimm.app.data.model.LoginModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

class LoginPresenter(private val view: LoginContract.View, activity: Activity) : LoginContract.Presenter, LoginContract.Model.LoginFinishedListener, LoginContract.Model.CheckAccountFinishedListener {
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

    override fun idExist(idToken: String?) {
        loginModel.googleLogin(idToken, this)
    }

    override fun idAbsence(idToken: String?) {
        view.startAgreementActivity(idToken)
    }

    override fun onFinished(googleToken: String) {
        view.startMainActivity()
    }

    override fun onFailure(t: Throwable) { }
}