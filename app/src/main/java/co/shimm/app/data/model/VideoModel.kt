package co.shimm.app.data.model

import android.content.Context
import android.net.Uri
import co.shimm.app.R
import co.shimm.app.data.player.Player
import co.shimm.app.data.room.Video
import co.shimm.app.data.room.VideoDao
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.view.fragment.video.VideoFragment
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class VideoModel(context: Context) {
    private val videoDao : VideoDao = ShimDatabase.getInstance(context).videoDao

    fun updateRecyclerViewData(adapter: VideoFragment.Page.ShimAdapter, position: Int){
        lateinit var updateThread : Thread
        when(position){
            0 -> updateThread = Thread { adapter.setItem(videoDao.getAll() as ArrayList<Video>)}
            1 -> updateThread = Thread { adapter.setItem(videoDao.findByCategory(0) as ArrayList<Video>)}
            2 -> updateThread = Thread { adapter.setItem(videoDao.findByCategory(1) as ArrayList<Video>)}
            3 -> updateThread = Thread { adapter.setItem(videoDao.findByCategory(2) as ArrayList<Video>)}
            4 -> updateThread = Thread { adapter.setItem(videoDao.findByCategory(2) as ArrayList<Video>)}
        }

        updateThread.start()

        try{
            updateThread.join()
        }catch (e: InterruptedException){
            e.printStackTrace()
        }

        adapter.setTabPosition(position)
    }

    fun playVideo(video: Video){
        val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(R.string.app_name.toString()))
            .createMediaSource(Uri.parse(video.src))
        Player.mainPlayer?.prepare(mediaSource)
        Player.mainPlayer?.playWhenReady = true
        Player.playerThumbnail = video.thumbnail
        Player.playerTitle = video.title
    }
}