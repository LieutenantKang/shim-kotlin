package co.shimm.app.view.activity.videoplaylist

import co.shimm.app.base.BaseContract
import co.shimm.app.data.room.entity.ShimCounselor
import co.shimm.app.data.room.entity.ShimVideoPlaylist

interface VideoPlaylistContract {
    interface View: BaseContract.BaseView<Presenter> {
    }

    interface Presenter: BaseContract.BasePresenter{
        fun initRecyclerViewData(adapter: VideoPlaylistActivity.VideoAdapter, listId: Int)
        fun getVideoPlaylist(listId: Int) : ShimVideoPlaylist?
        fun getCounselor(id: Int) : ShimCounselor
    }
}