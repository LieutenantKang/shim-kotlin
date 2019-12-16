package co.shimm.app.view.activity.detail

import android.app.Activity

class DetailPresenter(private val view: DetailContract.View, activity: Activity): DetailContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}