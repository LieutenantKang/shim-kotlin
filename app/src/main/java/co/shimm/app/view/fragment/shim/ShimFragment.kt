package co.shimm.app.view.fragment.shim

import android.os.Bundle
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseFragment

class ShimFragment : BaseFragment(), ShimContract.View {
    override val layoutRes: Int
        get() = R.layout.fragment_shim

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {

    }

    override lateinit var presenter: ShimContract.Presenter

    override fun isViewActive(): Boolean = checkActive()
}