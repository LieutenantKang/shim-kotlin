package co.shimm.app.view.activity.musicplayer

import android.app.Activity

class MusicPlayerPresenter(private val view: MusicPlayerContract.View, activity: Activity): MusicPlayerContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}