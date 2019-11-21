package co.shimm.app.data.model

import android.content.Context
import co.shimm.app.data.room.Shim
import co.shimm.app.view.fragment.home.HomeFragment

class HomeModel(context: Context) {
    fun initRecyclerViewData(adapter: HomeFragment.HomeAdapter){
        val shimList = ArrayList<Shim>()
        val shimFirst = Shim()
        shimFirst.title = "Title1"
        shimList.add(shimFirst)

        val shimSecond = Shim()
        shimSecond.title = "Title2"
        shimList.add(shimSecond)
        adapter.setItem(shimList)
        adapter.notifyDataSetChanged()
    }
}