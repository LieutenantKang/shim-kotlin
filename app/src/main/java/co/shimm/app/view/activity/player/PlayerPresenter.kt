package co.shimm.app.view.activity.player

import android.app.Activity

class PlayerPresenter(private val view: PlayerContract.View, activity: Activity): PlayerContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}