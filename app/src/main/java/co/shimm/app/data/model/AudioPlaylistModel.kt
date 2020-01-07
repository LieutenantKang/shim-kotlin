package co.shimm.app.data.model

import android.content.Context
import android.net.Uri
import co.shimm.app.R
import co.shimm.app.data.player.ShimPlayer
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.data.room.dao.ShimAudioDao
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.view.activity.audioplaylist.AudioPlaylistActivity
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class AudioPlaylistModel(val context: Context) {
    fun initRecyclerViewData(adapter: AudioPlaylistActivity.AudioAdapter, listId: Int){
        val shimAudioDao: ShimAudioDao = ShimDatabase.getInstance(context).shimAudioDao

        Thread { adapter.setItem(shimAudioDao.getAudios(listId) as ArrayList<ShimAudio>)}.start()

        adapter.notifyDataSetChanged()
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