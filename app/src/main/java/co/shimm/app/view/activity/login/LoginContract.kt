package co.shimm.app.view.activity.login

import co.shimm.app.base.BaseContract
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

interface LoginContract {
    interface Model{
        interface CheckAccountFinishedListener{
            fun idExist(idToken: String?)
            fun idAbsence(idToken: String?)
        }

        interface LoginFinishedListener{
            fun onFinished(googleToken : String)
            fun onFailure(t : Throwable)
        }

        fun signIn()
        fun checkAccount(task: Task<GoogleSignInAccount>, checkAccountFinishedListener: CheckAccountFinishedListener)
        fun googleLogin(idToken: String?, loginFinishedListener: LoginFinishedListener)
    }

    interface View: BaseContract.BaseView<Presenter>{
        fun startMainActivity()
        fun startAgreementActivity(idToken: String?)
    }

    interface Presenter: BaseContract.BasePresenter{
        fun signIn()
        fun checkAccount(task: Task<GoogleSignInAccount>)
    }
}