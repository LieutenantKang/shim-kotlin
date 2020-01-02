package co.shimm.app.view.activity.audioplayer

import android.content.Context

class AudioPlayerPresenter(private val view: AudioPlayerContract.View, context: Context): AudioPlayerContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}