package co.shimm.app.data.model

import android.content.Context
import android.net.Uri
import co.shimm.app.R
import co.shimm.app.data.player.ShimPlayer
import co.shimm.app.data.player.ShimPlayer.shimPlayIndex
import co.shimm.app.data.player.ShimPlayer.shimPlaylist
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.data.room.dao.ShimAudioDao
import co.shimm.app.data.room.dao.ShimAudioPlaylistDao
import co.shimm.app.data.room.dao.ShimCounselorDao
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.data.room.entity.ShimAudioPlaylist
import co.shimm.app.data.room.entity.ShimCounselor
import co.shimm.app.view.activity.audioplaylist.AudioPlaylistActivity
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class AudioPlaylistModel(val context: Context) {
    fun initRecyclerViewData(adapter: AudioPlaylistActivity.AudioAdapter, listId: Int){
        val shimAudioDao: ShimAudioDao = ShimDatabase.getInstance(context).shimAudioDao

        Thread { adapter.setItem(shimAudioDao.getAudios(listId) as ArrayList<ShimAudio>)}.start()

        adapter.notifyDataSetChanged()
    }

    fun playAudio(shimAudio: ShimAudio, index: Int){
        val shimAudioDao: ShimAudioDao = ShimDatabase.getInstance(context).shimAudioDao
        Thread { shimPlaylist = shimAudioDao.getAudios(shimAudio.playlistId!!) as ArrayList<ShimAudio>}.start()

        shimPlayIndex = index

        val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(R.string.app_name.toString()))
            .createMediaSource(Uri.parse(shimAudio.src))
        ShimPlayer.shimPlayer?.prepare(mediaSource)
        ShimPlayer.shimPlayer?.playWhenReady = true
        ShimPlayer.shimPlayerThumbnail = shimAudio.thumbnail
        ShimPlayer.shimPlayerTitle = shimAudio.title
    }

    fun getAudioPlaylist(listId: Int) : ShimAudioPlaylist? {
        val shimAudioPlaylistDao : ShimAudioPlaylistDao = ShimDatabase.getInstance(context).shimAudioPlaylistDao
        var shimAudioPlaylist = arrayListOf<ShimAudioPlaylist>()
        val thread = Thread { shimAudioPlaylist = shimAudioPlaylistDao.getAudioPlaylist(listId) as ArrayList<ShimAudioPlaylist> }
        thread.start()
        try { thread.join() } catch(e : Exception) { }

        return shimAudioPlaylist[0]
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