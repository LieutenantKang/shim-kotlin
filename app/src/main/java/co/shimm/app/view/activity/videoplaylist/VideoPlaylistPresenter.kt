package co.shimm.app.view.activity.videoplaylist

import android.content.Context
import co.shimm.app.data.model.VideoPlaylistModel
import co.shimm.app.data.room.entity.ShimCounselor
import co.shimm.app.data.room.entity.ShimVideoPlaylist

class VideoPlaylistPresenter(private val view: VideoPlaylistContract.View, context: Context): VideoPlaylistContract.Presenter {
    private var model: VideoPlaylistModel = VideoPlaylistModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun initRecyclerViewData(adapter: VideoPlaylistActivity.VideoAdapter, listId: Int) {
        model.initRecyclerViewData(adapter, listId)
    }

    override fun getVideoPlaylist(listId: Int): ShimVideoPlaylist? {
        return model.getVideoPlaylist(listId)
    }

    override fun getCounselor(id: Int): ShimCounselor {
        return model.getCounselor(id)
    }
}