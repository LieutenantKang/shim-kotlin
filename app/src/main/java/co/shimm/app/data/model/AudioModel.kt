package co.shimm.app.data.model

import android.content.Context
import co.shimm.app.data.room.dao.ShimAudioPlaylistDao
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.data.room.entity.ShimAudioPlaylist
import co.shimm.app.view.fragment.audio.AudioFragment

class AudioModel(context: Context) {
    private val shimAudioPlaylistDao : ShimAudioPlaylistDao = ShimDatabase.getInstance(context).shimAudioPlaylistDao

    fun updateRecyclerViewData(adapter: AudioFragment.Page.AudioPlaylistAdapter, position: Int) {
        lateinit var updateThread : Thread
        when (position){
            0 -> updateThread = Thread { adapter.setItem(shimAudioPlaylistDao.getAll() as ArrayList<ShimAudioPlaylist>)}
            1 -> updateThread = Thread { adapter.setItem(shimAudioPlaylistDao.findByCategory(0) as ArrayList<ShimAudioPlaylist>)}
            2 -> updateThread = Thread { adapter.setItem(shimAudioPlaylistDao.findByCategory(1) as ArrayList<ShimAudioPlaylist>)}
            3 -> updateThread = Thread { adapter.setItem(shimAudioPlaylistDao.findByCategory(2) as ArrayList<ShimAudioPlaylist>)}
            4 -> updateThread = Thread { adapter.setItem(shimAudioPlaylistDao.findByCategory(3) as ArrayList<ShimAudioPlaylist>)}
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