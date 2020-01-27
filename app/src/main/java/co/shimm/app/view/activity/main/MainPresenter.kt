package co.shimm.app.view.activity.main

import android.content.Context
import co.shimm.app.data.model.MainModel

class MainPresenter(private val view: MainContract.View, context: Context): MainContract.Presenter {
    private val mainModel: MainModel = MainModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun fetchData() {
        mainModel.fetchData()
    }

    override fun playNext() {
        mainModel.playNext()
    }

    override fun playPrevious() {
        mainModel.playPrevious()
    }
}