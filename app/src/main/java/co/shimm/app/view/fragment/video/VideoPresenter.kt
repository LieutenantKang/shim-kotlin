package co.shimm.app.view.fragment.video

import android.content.Context
import co.shimm.app.data.model.VideoModel

class VideoPresenter  (private val view: VideoContract.View, context : Context) : VideoContract.Presenter {
    private val videoModel : VideoModel = VideoModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun updateRecyclerViewData(adapter: VideoFragment.Page.VideoPlaylistAdapter, position: Int) {
        videoModel.updateRecyclerViewData(adapter, position)
    }
}