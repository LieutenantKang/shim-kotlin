package co.shimm.app.view.fragment.music

import android.content.Context
import co.shimm.app.base.BaseContract

interface MusicContract {
    interface View: BaseContract.BaseView<Presenter>{

    }

    interface Presenter: BaseContract.BasePresenter{
        fun updateRecyclerViewData(adapter : MusicFragment.Page.MusicAdapter, position: Int)
    }
}