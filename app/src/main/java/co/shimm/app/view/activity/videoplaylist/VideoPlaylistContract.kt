package co.shimm.app.view.activity.videoplaylist

import co.shimm.app.base.BaseContract

interface VideoPlaylistContract {
    interface View: BaseContract.BaseView<Presenter> {
    }

    interface Presenter: BaseContract.BasePresenter{
        fun initRecyclerViewData(adapter: VideoPlaylistActivity.VideoAdapter, listId: Int)
    }
}