package co.shimm.app.view.activity.musicplayer

import co.shimm.app.base.BaseContract

interface MusicPlayerContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter: BaseContract.BasePresenter{
    }
}