package co.shimm.app.view.activity.main

import co.shimm.app.R
import co.shimm.app.base.BaseActivity

class MainActivity : BaseActivity(), MainContract.View{
    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun initView() {
        presenter = MainPresenter(this)
        presenter.start()
    }

    override fun onClickEvent() {
        super.onClickEvent()
    }

    override lateinit var presenter: MainContract.Presenter

    override fun isViewActive(): Boolean = checkActive()
}
