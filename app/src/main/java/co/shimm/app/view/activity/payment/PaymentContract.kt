package co.shimm.app.view.activity.payment

import co.shimm.app.base.BaseContract

interface PaymentContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter : BaseContract.BasePresenter{
    }
}