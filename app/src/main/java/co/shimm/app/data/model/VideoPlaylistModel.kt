package co.shimm.app.data.model

import android.content.Context
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.data.room.dao.ShimCounselorDao
import co.shimm.app.data.room.dao.ShimVideoDao
import co.shimm.app.data.room.dao.ShimVideoPlaylistDao
import co.shimm.app.data.room.entity.ShimCounselor
import co.shimm.app.data.room.entity.ShimVideo
import co.shimm.app.data.room.entity.ShimVideoPlaylist
import co.shimm.app.view.activity.videoplaylist.VideoPlaylistActivity
import java.lang.Exception

class VideoPlaylistModel(val context: Context) {
    fun initRecyclerViewData(adapter: VideoPlaylistActivity.VideoAdapter, listId: Int){
        val shimVideoDao : ShimVideoDao = ShimDatabase.getInstance(context).shimVideoDao

        Thread { adapter.setItem(shimVideoDao.getVideos(listId) as ArrayList<ShimVideo>)}.start()

        adapter.notifyDataSetChanged()
    }

    fun getVideoPlaylist(listId: Int) : ShimVideoPlaylist? {
        val shimVideoPlaylistDao : ShimVideoPlaylistDao = ShimDatabase.getInstance(context).shimVideoPlaylistDao
        var shimVideoPlaylist = arrayListOf<ShimVideoPlaylist>()
        val thread = Thread { shimVideoPlaylist = shimVideoPlaylistDao.getVideoPlaylist(listId) as ArrayList<ShimVideoPlaylist> }
        thread.start()
        try { thread.join() } catch(e: Exception) { }
        return shimVideoPlaylist[0]
    }

    fun getCounselor(id: Int) : ShimCounselor {
        val shimCounselorDao : ShimCounselorDao = ShimDatabase.getInstance(context).shimCounselorDao
        var shimCounselor = arrayListOf<ShimCounselor>()
        val thread = Thread { shimCounselor = shimCounselorDao.getCounselor(id) as ArrayList<ShimCounselor> }
        thread.start()
        try { thread.join() } catch(e : Exception) { }

        return shimCounselor[0]
    }
}