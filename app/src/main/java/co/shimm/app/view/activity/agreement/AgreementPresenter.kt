package co.shimm.app.view.activity.agreement

import android.content.Context
import co.shimm.app.data.model.AgreementModel

class AgreementPresenter(private val view: AgreementContract.View, context: Context): AgreementContract.Presenter, AgreementContract.Model.LoginFinishedListener, AgreementContract.Model.AgreementFinishedListener {
    private val model: AgreementModel = AgreementModel()

    override fun start() {
        view.presenter = this
    }

    override fun googleLogin(idToken: String?) {
        model.googleLogin(idToken, this)
    }

    override fun onFinished(googleToken: String) {
        model.agreeTerms(this)
    }

    override fun onFailure(t: Throwable) { }

    override fun agreeFinished() {
        view.startMainActivity()
    }

    override fun agreeFailed() { }
}