package co.shimm.app.view.activity.main

import co.shimm.app.base.BaseContract

interface MainContract {
    interface View: BaseContract.BaseView<Presenter>

    interface Presenter: BaseContract.BasePresenter{
        fun fetchData()
        fun playNext()
        fun playPrevious()
    }
}