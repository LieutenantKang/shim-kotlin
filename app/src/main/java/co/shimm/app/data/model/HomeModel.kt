package co.shimm.app.data.model

import android.content.Context
import co.shimm.app.data.room.Shim

class HomeModel(context: Context) {
    fun loadHome(): ArrayList<Shim>{
        val shimList = ArrayList<Shim>()
        var shimFirst = Shim()
        shimFirst.title = "Title1"
        shimList.add(shimFirst)

        var shimSecond = Shim()
        shimSecond.title = "Title2"
        shimList.add(shimSecond)

        return shimList
    }
}