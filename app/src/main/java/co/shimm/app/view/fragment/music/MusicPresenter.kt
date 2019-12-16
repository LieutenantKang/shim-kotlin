package co.shimm.app.view.fragment.music

import android.content.Context
import co.shimm.app.data.model.MusicModel
import co.shimm.app.data.room.Music

class MusicPresenter (private val view: MusicContract.View, context: Context) : MusicContract.Presenter {
    private val musicModel: MusicModel = MusicModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun updateRecyclerViewData(adapter: MusicFragment.Page.MusicAdapter, position: Int) {
        musicModel.updateRecyclerViewData(adapter, position)
    }

    override fun playMusic(music: Music) {
        musicModel.playMusic(music)
    }
}