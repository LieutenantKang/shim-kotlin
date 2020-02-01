package co.shimm.app.data.player

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.IBinder
import co.shimm.app.R
import co.shimm.app.data.player.ShimPlayerData.shimPlayer
import co.shimm.app.data.player.ShimPlayerData.shimPlaylist
import co.shimm.app.view.activity.audioplayer.AudioPlayerActivity
import co.shimm.app.view.activity.main.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class ShimPlayerService : Service() {
//    lateinit var shimPlayer : SimpleExoPlayer
    lateinit var shimNotificationManager : PlayerNotificationManager

    override fun onCreate() {
        super.onCreate()
        val context : Context = this

        addListener()
        setNotificationManager(context)
    }

    private fun addListener(){
        val eventListener = object : Player.EventListener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                ShimPlayerData.shimPlayerTitle = shimPlaylist!![shimPlayer?.currentWindowIndex!!].title.toString()
                if(playbackState == Player.STATE_READY){
                    PlayerEventBus.post(
                        PlayerData(
                            shimPlaylist!![shimPlayer?.currentWindowIndex!!].title.toString(),
                            shimPlaylist!![shimPlayer?.currentWindowIndex!!].thumbnail.toString()
                        )
                    )
                }
                super.onPlayerStateChanged(playWhenReady, playbackState)
            }
        }
        shimPlayer?.addListener(eventListener)
    }

    override fun onDestroy() {
        shimNotificationManager.setPlayer(null)
        super.onDestroy()
    }

//    fun playAudio(index: Int) {
//        val dataSourceFactory = DefaultDataSourceFactory(mainContext, Util.getUserAgent(mainContext, "Shim"))
//        val concatenatingMediaSource = ConcatenatingMediaSource()
//
//        for(shim in shimPlaylist.orEmpty()){
//            val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(shim.src))
//            concatenatingMediaSource.addMediaSource(mediaSource)
//        }
//        shimPlayer?.prepare(concatenatingMediaSource)
//        shimPlayer?.seekTo(index, C.TIME_UNSET)
//        shimPlayer?.playWhenReady = true
//
//        setNotificationManager(mainContext)
//    }

    private fun setNotificationManager(context: Context){

        shimNotificationManager = PlayerNotificationManager.createWithNotificationChannel(context, "7", R.string.app_name, R.string.app_name,
            object : PlayerNotificationManager.MediaDescriptionAdapter{

                override fun createCurrentContentIntent(player: Player?): PendingIntent? {
                    val intent = Intent(context, MainActivity::class.java)
                    return PendingIntent.getActivity(context, 8888, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                }

                override fun getCurrentContentText(player: Player?): String? {
                    return ShimPlayerData.shimPlayerCounselor?.name
                }

                override fun getCurrentContentTitle(player: Player?): String {
                    return shimPlaylist?.get(shimPlayer?.currentWindowIndex!!)?.title.toString()
                }

                override fun getCurrentLargeIcon(player: Player?, callback: PlayerNotificationManager.BitmapCallback?): Bitmap? {
                    var iconBitmap : Bitmap? = null
                    Glide.with(context).asBitmap().load(ShimPlayerData.shimPlayerThumbnail)
                        .into(object : SimpleTarget<Bitmap>(){
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                iconBitmap = resource
                            } })
                    return iconBitmap
                }
            })

        shimNotificationManager.setNotificationListener(
            object : PlayerNotificationManager.NotificationListener{
                override fun onNotificationStarted(
                    notificationId: Int,
                    notification: Notification?
                ) {
                    startForeground(notificationId,notification)
                }
                override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
                    stopSelf()
                }
            }
        )
        shimNotificationManager.setRewindIncrementMs(0)
        shimNotificationManager.setFastForwardIncrementMs(0)
        shimNotificationManager.setUseStopAction(true)

        shimNotificationManager.setPlayer(shimPlayer)
    }

    override fun onBind(intent: Intent?): IBinder? { return null }
}