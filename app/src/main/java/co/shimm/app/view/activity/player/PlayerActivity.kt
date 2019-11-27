package co.shimm.app.view.activity.player

import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseActivity

class PlayerActivity : BaseActivity(), PlayerContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_player

    override fun initView() {
        presenter = PlayerPresenter(this@PlayerActivity, this)
        presenter.start()
    }

    override lateinit var presenter: PlayerContract.Presenter

    override fun onClick(p0: View?) {

    }

    override fun isViewActive(): Boolean = checkActive()
}
