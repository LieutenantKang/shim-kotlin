package co.shimm.app.view.activity.videoplayer

import co.shimm.app.base.BaseContract

interface VideoPlayerContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter : BaseContract.BasePresenter{
    }
}