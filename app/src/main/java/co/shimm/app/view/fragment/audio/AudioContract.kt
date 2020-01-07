package co.shimm.app.view.fragment.audio

import co.shimm.app.base.BaseContract
import co.shimm.app.data.room.entity.ShimAudio

interface AudioContract {
    interface View: BaseContract.BaseView<Presenter>

    interface Presenter: BaseContract.BasePresenter{
        fun updateRecyclerViewData(adapter : AudioFragment.Page.AudioPlaylistAdapter, position: Int)
    }
}