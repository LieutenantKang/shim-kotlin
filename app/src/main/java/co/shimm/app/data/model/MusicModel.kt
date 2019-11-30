package co.shimm.app.data.model

import android.content.Context
import co.shimm.app.data.room.Music
import co.shimm.app.data.room.MusicDao
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.view.fragment.music.MusicFragment

class MusicModel(context: Context) {
    private val musicDao : MusicDao = ShimDatabase.getInstance(context).musicDao

    fun updateRecyclerViewData(adapter: MusicFragment.Page.MusicAdapter, position: Int) {
        lateinit var updateThread : Thread
        when (position){
            0 -> updateThread = Thread { adapter.setItem(musicDao.getAll() as ArrayList<Music>)}
            1 -> updateThread = Thread { adapter.setItem(musicDao.findByCategory(0) as ArrayList<Music>)}
            2 -> updateThread = Thread { adapter.setItem(musicDao.findByCategory(0) as ArrayList<Music>)}
            3 -> updateThread = Thread { adapter.setItem(musicDao.findByCategory(1) as ArrayList<Music>)}
            4 -> updateThread = Thread { adapter.setItem(musicDao.findByCategory(2) as ArrayList<Music>)}
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