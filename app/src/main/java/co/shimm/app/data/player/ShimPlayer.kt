package co.shimm.app.data.player

import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.data.room.entity.ShimCounselor
import com.google.android.exoplayer2.SimpleExoPlayer

object ShimPlayer {
    var shimPlayer: SimpleExoPlayer? = null
    var shimPlayerThumbnail : String? = null
    var shimPlayerTitle : String ? = null
    var shimPlayerCounselor : ShimCounselor? = null

    var shimPlaylist: List<ShimAudio>? = null
    var shimPlayIndex: Int? = null
}