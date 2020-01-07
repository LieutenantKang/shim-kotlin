package co.shimm.app.view.activity.audioplayer

import co.shimm.app.base.BaseContract

interface AudioPlayerContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter: BaseContract.BasePresenter{
        fun playNext()
        fun playPrevious()
    }
}