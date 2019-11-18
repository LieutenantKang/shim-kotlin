package co.shimm.app.view.fragment.music

import android.os.Bundle
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseFragment

class MusicFragment : BaseFragment(), MusicContract.View {
    override val layoutRes: Int
    get() = R.layout.fragment_music

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {

    }

    override lateinit var presenter: MusicContract.Presenter

    override fun isViewActive(): Boolean = checkActive()
}