package co.shimm.app.view.activity.audioplaylist

import co.shimm.app.base.BaseContract
import co.shimm.app.data.room.entity.ShimAudio

interface AudioPlaylistContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter: BaseContract.BasePresenter{
        fun initRecyclerViewData(adapter: AudioPlaylistActivity.AudioAdapter, listId: Int)
        fun playAudio(audio: ShimAudio)
    }
}