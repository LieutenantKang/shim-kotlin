package co.shimm.app.view.activity.setting

import co.shimm.app.base.BaseContract

interface SettingContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter : BaseContract.BasePresenter{
        fun logout()
    }
}