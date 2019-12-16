package co.shimm.app.view.fragment.music

import co.shimm.app.base.BaseContract
import co.shimm.app.data.room.Music

interface MusicContract {
    interface View: BaseContract.BaseView<Presenter>

    interface Presenter: BaseContract.BasePresenter{
        fun updateRecyclerViewData(adapter : MusicFragment.Page.MusicAdapter, position: Int)
        fun playMusic(music : Music)
    }
}