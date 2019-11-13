package co.shimm.app.view.adapter

import co.shimm.app.data.room.Shim

interface AdapterContract {
    interface View{
        fun notifyAdapter()
    }

    interface Model{
        fun addItems(list : ArrayList<Shim>)
        fun clearItem()
    }
}