package co.shimm.app.view.activity.audioplaylist

import android.content.Context
import co.shimm.app.data.model.AudioPlaylistModel
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.data.room.entity.ShimAudioPlaylist
import co.shimm.app.data.room.entity.ShimCounselor

class AudioPlaylistPresenter(private val view: AudioPlaylistContract.View, context: Context): AudioPlaylistContract.Presenter  {
    private var model: AudioPlaylistModel = AudioPlaylistModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun initRecyclerViewData(adapter: AudioPlaylistActivity.AudioAdapter, listId: Int) {
        model.initRecyclerViewData(adapter, listId)
    }

    override fun playAudio(shimAudio: ShimAudio, index: Int) {
        model.playAudio(shimAudio, index)
    }

    override fun getAudioPlaylist(listId: Int): ShimAudioPlaylist? {
        return model.getAudioPlaylist(listId)
    }

    override fun getCounselor(id: Int): ShimCounselor {
        return model.getCounselor(id)
    }
}