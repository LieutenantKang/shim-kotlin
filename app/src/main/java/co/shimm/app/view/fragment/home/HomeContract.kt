package co.shimm.app.view.fragment.home

import co.shimm.app.base.BaseContract

interface HomeContract {
    interface View: BaseContract.BaseView<Presenter>

    interface Presenter: BaseContract.BasePresenter {
        fun initRecyclerViewData(adapter: HomeFragment.HomeAdapter)
    }
}
