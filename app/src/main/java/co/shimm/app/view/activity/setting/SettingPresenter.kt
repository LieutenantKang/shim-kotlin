package co.shimm.app.view.activity.setting

import android.app.Activity
import android.content.Context
import co.shimm.app.data.model.SettingModel

class SettingPresenter(private val view : SettingContract.View, val activity: Activity) : SettingContract.Presenter {
    private val model : SettingModel = SettingModel(activity)

    override fun start() {
        view.presenter = this
    }

    override fun logout() {
        model.logout()
    }
}