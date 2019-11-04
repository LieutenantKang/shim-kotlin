package co.shimm.app.view.fragment.home

import android.os.Bundle
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseFragment

class HomeFragment : BaseFragment(), HomeContract.View {
    override val layoutRes: Int
        get() = R.layout.fragment_home

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {

    }

    override fun onClickEvent() {
        super.onClickEvent()
    }

    override lateinit var presenter: HomeContract.Presenter

    override fun isViewActive(): Boolean = checkActive()
}