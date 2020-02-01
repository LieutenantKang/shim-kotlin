package co.shimm.app.view.activity.counselor

import co.shimm.app.base.BaseContract

interface CounselorContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter: BaseContract.BasePresenter{
    }
}