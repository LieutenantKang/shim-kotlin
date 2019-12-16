package co.shimm.app.view.activity.detail

import co.shimm.app.base.BaseContract

interface DetailContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter: BaseContract.BasePresenter{
    }
}