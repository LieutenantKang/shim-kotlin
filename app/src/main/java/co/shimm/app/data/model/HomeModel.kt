package co.shimm.app.data.model

import android.content.Context
import co.shimm.app.data.room.entity.ShimVideo
import co.shimm.app.view.fragment.home.HomeFragment

class HomeModel(context: Context) {
    fun initRecyclerViewData(adapter: HomeFragment.HomeAdapter){
        val shimVideoList = ArrayList<ShimVideo>()
//        val shimFirst = ShimVideo(1,"home_title_1", 1, "", "", "", 10)
//        shimList.add(shimFirst)
//
//        val shimSecond = ShimVideo(2,"home_title_2", 1, "", "", "", 10)
//        shimList.add(shimSecond)
        adapter.setItem(shimVideoList)
        adapter.notifyDataSetChanged()
    }
}