package co.shimm.app.view.activity.main

import android.content.Context

class MainPresenter(private val view: MainContract.View, private val context: Context): MainContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}