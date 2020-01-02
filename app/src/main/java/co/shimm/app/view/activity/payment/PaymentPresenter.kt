package co.shimm.app.view.activity.payment

import android.content.Context

class PaymentPresenter(private val view: PaymentContract.View, context: Context): PaymentContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}