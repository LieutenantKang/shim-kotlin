package co.shimm.app.data.model

import android.net.Uri
import android.util.Log
import co.shimm.app.R
import co.shimm.app.data.player.ShimPlayerData.shimPlayIndex
import co.shimm.app.data.player.ShimPlayerData.shimPlayer
import co.shimm.app.data.player.ShimPlayerData.shimPlayerTitle
import co.shimm.app.data.player.ShimPlayerData.shimPlaylist
import co.shimm.app.data.room.entity.ShimAudio
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class AudioPlayerModel {
    fun playNext(){
        Log.d("BEFORE INDEX", shimPlayIndex.toString())
        shimPlayIndex = shimPlayIndex?.plus(1)
        if(shimPlayIndex == shimPlaylist?.size){
            shimPlayIndex = 0
        }
        Log.d("PlayNEXTINDEX", shimPlayIndex.toString())
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