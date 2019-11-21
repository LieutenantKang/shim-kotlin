package co.shimm.app.view.fragment.home

import android.content.Context
import co.shimm.app.data.model.HomeModel

class HomePresenter(private val view: HomeContract.View, context: Context) : HomeContract.Presenter {
    private var model : HomeModel = HomeModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun initRecyclerViewData(adapter: HomeFragment.HomeAdapter) {
        model.initRecyclerViewData(adapter)
    }
}