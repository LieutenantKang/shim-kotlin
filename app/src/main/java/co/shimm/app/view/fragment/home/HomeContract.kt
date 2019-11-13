package co.shimm.app.view.fragment.home

import co.shimm.app.base.BaseContract
import co.shimm.app.data.model.HomeModel
import co.shimm.app.view.adapter.AdapterContract

interface HomeContract {
    interface View: BaseContract.BaseView<Presenter>{

    }

    interface Presenter: BaseContract.BasePresenter {
        var model: HomeModel

        var adapterView: AdapterContract.View
        var adapterModel: AdapterContract.Model

        fun initHomeList(isClear: Boolean)
    }
}
