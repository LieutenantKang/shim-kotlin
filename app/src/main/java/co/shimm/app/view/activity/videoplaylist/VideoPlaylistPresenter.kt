package co.shimm.app.view.activity.videoplaylist

import android.content.Context
import co.shimm.app.data.model.VideoPlaylistModel

class VideoPlaylistPresenter(private val view: VideoPlaylistContract.View, context: Context): VideoPlaylistContract.Presenter {
    private var model: VideoPlaylistModel = VideoPlaylistModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun initRecyclerViewData(adapter: VideoPlaylistActivity.VideoAdapter, listId: Int) {
        model.initRecyclerViewData(adapter, listId)
    }
}