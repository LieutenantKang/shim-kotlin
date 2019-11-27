package co.shimm.app.view.fragment.shim

import android.content.Context
import co.shimm.app.data.model.ShimModel

class ShimPresenter  (private val view: ShimContract.View, context : Context) : ShimContract.Presenter {
    private val shimModel : ShimModel = ShimModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun updateRecyclerViewData(adapter: ShimFragment.Page.ShimAdapter, position: Int) {
        shimModel.updateRecyclerViewData(adapter, position)
    }
}