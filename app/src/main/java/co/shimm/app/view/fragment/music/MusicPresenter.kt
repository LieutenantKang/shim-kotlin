package co.shimm.app.view.fragment.music

import android.content.Context
import co.shimm.app.data.model.MusicModel

class MusicPresenter (private val view: MusicContract.View, context: Context) : MusicContract.Presenter {
    private val musicModel: MusicModel = MusicModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun updateRecyclerViewData(adapter: MusicFragment.Page.MusicAdapter, position: Int) {
        musicModel.updateRecyclerViewData(adapter, position)
    }
}