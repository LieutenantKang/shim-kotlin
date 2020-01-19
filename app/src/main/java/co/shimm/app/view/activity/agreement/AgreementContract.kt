package co.shimm.app.view.activity.agreement

import co.shimm.app.base.BaseContract

interface AgreementContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter: BaseContract.BasePresenter{
    }
}