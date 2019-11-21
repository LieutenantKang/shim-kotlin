package co.shimm.app.view.fragment.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.shimm.app.R
import co.shimm.app.base.BaseFragment
import co.shimm.app.view.adapter.HomeAdapter

class HomeFragment : BaseFragment(), HomeContract.View {
    override val layoutRes: Int
        get() = R.layout.fragment_home

    private val recyclerView by lazy{
        view?.findViewById<RecyclerView>(R.id.home_recycler_view)
    }

    private lateinit var homeAdapter : HomeAdapter
    override lateinit var presenter: HomeContract.Presenter

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {
        homeAdapter = HomeAdapter(requireContext())
        recyclerView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView?.adapter = homeAdapter

        presenter=HomePresenter(this@HomeFragment, requireContext()).apply{
            adapterModel=homeAdapter
            adapterView=homeAdapter
        }

        presenter.initHomeList(false)
    }
    override fun isViewActive(): Boolean = checkActive()
}