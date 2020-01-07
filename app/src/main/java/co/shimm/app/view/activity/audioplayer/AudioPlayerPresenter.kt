package co.shimm.app.view.activity.audioplayer

import android.content.Context
import co.shimm.app.data.model.AudioPlayerModel

class AudioPlayerPresenter(private val view: AudioPlayerContract.View, context: Context): AudioPlayerContract.Presenter {
    private var model: AudioPlayerModel = AudioPlayerModel()

    override fun start() {
        view.presenter = this
    }

    override fun playNext() {
        model.playNext()
    }

    override fun playPrevious() {
        model.playPrevious()
    }
}