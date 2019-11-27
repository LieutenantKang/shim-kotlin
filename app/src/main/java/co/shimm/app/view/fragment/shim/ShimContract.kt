package co.shimm.app.view.fragment.shim

import co.shimm.app.base.BaseContract

interface ShimContract {
    interface View: BaseContract.BaseView<Presenter>

    interface Presenter: BaseContract.BasePresenter{
        fun updateRecyclerViewData(adapter : ShimFragment.Page.ShimAdapter, position: Int)
    }
}