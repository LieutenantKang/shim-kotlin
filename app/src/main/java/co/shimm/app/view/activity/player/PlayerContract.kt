package co.shimm.app.view.activity.player

import co.shimm.app.base.BaseContract

interface PlayerContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter: BaseContract.BasePresenter{
    }
}