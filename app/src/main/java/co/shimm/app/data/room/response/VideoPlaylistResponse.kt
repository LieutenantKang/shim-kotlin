package co.shimm.app.data.room.response

import co.shimm.app.data.room.entity.ShimVideoPlaylist

data class VideoPlaylistResponse(val status : Int, val videoPlaylists : List<ShimVideoPlaylist>?)