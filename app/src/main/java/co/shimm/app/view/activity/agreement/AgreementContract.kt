package co.shimm.app.view.activity.agreement

import co.shimm.app.base.BaseContract

interface AgreementContract {
    interface Model{
        interface LoginFinishedListener{
            fun onFinished(googleToken : String)
            fun onFailure(t : Throwable)
        }

        interface AgreementFinishedListener{
            fun agreeFinished()
            fun agreeFailed()
        }

        fun googleLogin(idToken: String?, loginFinishedListener: LoginFinishedListener)
        fun agreeTerms(agreementFinishedListener: AgreementFinishedListener)
    }

    interface View: BaseContract.BaseView<Presenter>{
        fun startMainActivity()
    }

    interface Presenter: BaseContract.BasePresenter{
        fun googleLogin(idToken: String?)
    }
}