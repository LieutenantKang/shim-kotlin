package co.shimm.app.view.activity.terms

import android.content.Context

class TermsPresenter(private val view: TermsContract.View, context: Context): TermsContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}