package co.shimm.app.data.model

import android.content.Context
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.data.room.dao.ShimVideoDao
import co.shimm.app.data.room.entity.ShimVideo
import co.shimm.app.view.activity.videoplaylist.VideoPlaylistActivity

class VideoPlaylistModel(val context: Context) {
    fun initRecyclerViewData(adapter: VideoPlaylistActivity.VideoAdapter, listId: Int){
        val shimVideoDao : ShimVideoDao = ShimDatabase.getInstance(context).shimVideoDao

        Thread { adapter.setItem(shimVideoDao.getVideos(listId) as ArrayList<ShimVideo>)}.start()

        adapter.notifyDataSetChanged()
    }
}