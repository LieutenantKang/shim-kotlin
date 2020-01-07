package co.shimm.app.view.fragment.audio

import android.content.Context
import co.shimm.app.data.model.AudioModel
import co.shimm.app.data.room.entity.ShimAudio

class AudioPresenter (private val view: AudioContract.View, context: Context) : AudioContract.Presenter {
    private val audioModel: AudioModel = AudioModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun updateRecyclerViewData(adapter: AudioFragment.Page.AudioPlaylistAdapter, position: Int) {
        audioModel.updateRecyclerViewData(adapter, position)
    }
}