package co.shimm.app.data.model

import android.content.Context
import co.shimm.app.data.room.Shim
import co.shimm.app.data.room.ShimDao
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.view.fragment.shim.ShimFragment

class ShimModel(context: Context) {
    private val shimDao : ShimDao = ShimDatabase.getInstance(context).shimDao

    fun updateRecyclerViewData(adapter: ShimFragment.Page.ShimAdapter, position: Int){
        lateinit var updateThread : Thread
        when(position){
            0 -> updateThread = Thread { adapter.setItem(shimDao.getAll() as ArrayList<Shim>)}
            1 -> updateThread = Thread { adapter.setItem(shimDao.findByCategory(1) as ArrayList<Shim>)}
            2 -> updateThread = Thread { adapter.setItem(shimDao.findByCategory(1) as ArrayList<Shim>)}
            3 -> updateThread = Thread { adapter.setItem(shimDao.findByCategory(2) as ArrayList<Shim>)}
            4 -> updateThread = Thread { adapter.setItem(shimDao.findByCategory(2) as ArrayList<Shim>)}
        }

        updateThread.start()

        try{
            updateThread.join()
        }catch (e: InterruptedException){
            e.printStackTrace()
        }

        adapter.setTabPosition(position)
    }
}