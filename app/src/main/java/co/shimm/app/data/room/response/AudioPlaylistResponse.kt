package co.shimm.app.data.room.response

import co.shimm.app.data.room.entity.ShimAudioPlaylist

data class AudioPlaylistResponse (val status : Int, val audioPlaylists : List<ShimAudioPlaylist>?)