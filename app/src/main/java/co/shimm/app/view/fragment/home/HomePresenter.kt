package co.shimm.app.view.fragment.home

import android.content.Context
import co.shimm.app.data.model.HomeModel
import co.shimm.app.view.adapter.AdapterContract

class HomePresenter(private val view: HomeContract.View, private val context: Context) : HomeContract.Presenter {
    override var model : HomeModel = HomeModel(context)

    override lateinit var adapterModel : AdapterContract.Model
    override lateinit var adapterView : AdapterContract.View

    override fun start() {
        view.presenter = this
    }

    override fun initHomeList(isClear: Boolean){
        model.loadHome().let{
            if(isClear){
                adapterModel.clearItem()
            }
            adapterModel.addItems(it)
            adapterView.notifyAdapter()
        }
    }
}