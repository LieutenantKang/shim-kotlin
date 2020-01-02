package co.shimm.app.view.fragment.video

import co.shimm.app.base.BaseContract

interface VideoContract {
    interface View: BaseContract.BaseView<Presenter>

    interface Presenter: BaseContract.BasePresenter{
        fun updateRecyclerViewData(adapter : VideoFragment.Page.VideoPlaylistAdapter, position: Int)
    }
}