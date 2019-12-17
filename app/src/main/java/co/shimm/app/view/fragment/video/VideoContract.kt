package co.shimm.app.view.fragment.video

import co.shimm.app.base.BaseContract
import co.shimm.app.data.room.Video

interface VideoContract {
    interface View: BaseContract.BaseView<Presenter>

    interface Presenter: BaseContract.BasePresenter{
        fun updateRecyclerViewData(adapter : VideoFragment.Page.ShimAdapter, position: Int)
        fun playVideo(video: Video)
    }
}