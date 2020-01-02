package co.shimm.app.data.model

import android.content.Context
import android.net.Uri
import co.shimm.app.R
import co.shimm.app.data.player.ShimPlayer
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.data.room.dao.ShimAudioPlaylistDao
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.view.fragment.audio.AudioFragment
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class AudioModel(context: Context) {
    private val shimAudioPlaylistDao : ShimAudioPlaylistDao = ShimDatabase.getInstance(context).shimAudioPlaylistDao

    fun updateRecyclerViewData(adapter: AudioFragment.Page.AudioAdapter, position: Int) {
        lateinit var updateThread : Thread
        when (position){
//            0 -> updateThread = Thread { adapter.setItem(shimAudioPlaylistDao.getAll() as ArrayList<ShimAudio>)}
//            1 -> updateThread = Thread { adapter.setItem(shimAudioPlaylistDao.findByCategory(0) as ArrayList<ShimAudio>)}
//            2 -> updateThread = Thread { adapter.setItem(shimAudioPlaylistDao.findByCategory(1) as ArrayList<ShimAudio>)}
//            3 -> updateThread = Thread { adapter.setItem(shimAudioPlaylistDao.findByCategory(2) as ArrayList<ShimAudio>)}
//            4 -> updateThread = Thread { adapter.setItem(shimAudioPlaylistDao.findByCategory(3) as ArrayList<ShimAudio>)}
        }

        updateThread.start()

        try{
            updateThread.join()
        }catch (e: InterruptedException){
            e.printStackTrace()
        }

        adapter.setTabPosition(position)
    }

    fun playAudio(shimAudio: ShimAudio){
        val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(R.string.app_name.toString()))
            .createMediaSource(Uri.parse(shimAudio.src))
        ShimPlayer.shimPlayer?.prepare(mediaSource)
        ShimPlayer.shimPlayer?.playWhenReady = true
        ShimPlayer.shimPlayerThumbnail = shimAudio.thumbnail
        ShimPlayer.shimPlayerTitle = shimAudio.title
    }
}