package co.shimm.app.view.activity.agreement

import android.content.Context

class AgreementPresenter(private val view: AgreementContract.View, context: Context): AgreementContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}