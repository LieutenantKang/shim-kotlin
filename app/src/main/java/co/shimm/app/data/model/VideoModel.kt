package co.shimm.app.data.model

import android.content.Context
import android.net.Uri
import co.shimm.app.R
import co.shimm.app.data.player.ShimPlayerData
import co.shimm.app.data.room.entity.ShimVideo
import co.shimm.app.data.room.dao.ShimVideoPlaylistDao
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.data.room.entity.ShimVideoPlaylist
import co.shimm.app.view.fragment.video.VideoFragment
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class VideoModel(context: Context) {
    private val shimVideoPlaylistDao : ShimVideoPlaylistDao = ShimDatabase.getInstance(context).shimVideoPlaylistDao

    fun updateRecyclerViewData(adapter: VideoFragment.Page.VideoPlaylistAdapter, position: Int){
        lateinit var updateThread : Thread
        when(position){
            0 -> updateThread = Thread { adapter.setItem(shimVideoPlaylistDao.getAll() as ArrayList<ShimVideoPlaylist>)}
            1 -> updateThread = Thread { adapter.setItem(shimVideoPlaylistDao.findByCategory(1) as ArrayList<ShimVideoPlaylist>)}
            2 -> updateThread = Thread { adapter.setItem(shimVideoPlaylistDao.findByCategory(2) as ArrayList<ShimVideoPlaylist>)}
            3 -> updateThread = Thread { adapter.setItem(shimVideoPlaylistDao.findByCategory(1) as ArrayList<ShimVideoPlaylist>)}
            4 -> updateThread = Thread { adapter.setItem(shimVideoPlaylistDao.findByCategory(2) as ArrayList<ShimVideoPlaylist>)}
        }

        updateThread.start()

        try{
            updateThread.join()
        }catch (e: InterruptedException){
            e.printStackTrace()
        }

        adapter.setTabPosition(position)
    }

    fun playVideo(shimVideo: ShimVideo){
        val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(R.string.app_name.toString()))
            .createMediaSource(Uri.parse(shimVideo.src))
        ShimPlayerData.shimPlayer?.prepare(mediaSource)
        ShimPlayerData.shimPlayer?.playWhenReady = true
        ShimPlayerData.shimPlayerThumbnail = shimVideo.thumbnail
        ShimPlayerData.shimPlayerTitle = shimVideo.title
    }
}