package co.shimm.app.view.activity.login

import co.shimm.app.base.BaseContract
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

interface LoginContract {
    interface View: BaseContract.BaseView<Presenter>{

    }

    interface Presenter: BaseContract.BasePresenter{
        fun signIn()
        fun checkAccount(task: Task<GoogleSignInAccount>)
    }
}