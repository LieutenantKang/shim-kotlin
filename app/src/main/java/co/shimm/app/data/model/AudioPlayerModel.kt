package co.shimm.app.data.model

import android.net.Uri
import co.shimm.app.R
import co.shimm.app.data.player.ShimPlayer.shimPlayIndex
import co.shimm.app.data.player.ShimPlayer.shimPlayer
import co.shimm.app.data.player.ShimPlayer.shimPlayerThumbnail
import co.shimm.app.data.player.ShimPlayer.shimPlayerTitle
import co.shimm.app.data.player.ShimPlayer.shimPlaylist
import co.shimm.app.data.room.entity.ShimAudio
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class AudioPlayerModel {
    fun playNext(){
        shimPlayIndex = shimPlayIndex?.plus(1)
        if(shimPlayIndex == shimPlaylist?.size){
            shimPlayIndex = 0
        }

        val shimAudio = shimPlaylist?.get(shimPlayIndex!!)
        playAudio(shimAudio)
    }

    fun playPrevious(){
        shimPlayIndex = shimPlayIndex?.minus(1)
        if(shimPlayIndex == -1){
            shimPlayIndex = shimPlaylist?.size?.minus(1)
        }

        val shimAudio = shimPlaylist?.get(shimPlayIndex!!)
        playAudio(shimAudio)
    }

    private fun playAudio(shimAudio: ShimAudio?){
        val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(R.string.app_name.toString()))
            .createMediaSource(Uri.parse(shimAudio?.src))
        shimPlayer?.prepare(mediaSource)
        shimPlayer?.playWhenReady = true
        shimPlayerTitle = shimAudio?.title
    }
}