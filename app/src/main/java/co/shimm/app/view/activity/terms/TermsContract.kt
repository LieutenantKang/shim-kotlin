package co.shimm.app.view.activity.terms

import co.shimm.app.base.BaseContract

interface TermsContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter: BaseContract.BasePresenter{
    }
}