package co.shimm.app.view.activity.audioplayer

import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.player.PlayerData
import co.shimm.app.data.player.PlayerEventBus
import co.shimm.app.data.player.ShimPlayer.shimPlayer
import co.shimm.app.data.player.ShimPlayer.shimPlayerCounselor
import co.shimm.app.data.player.ShimPlayer.shimPlayerThumbnail
import co.shimm.app.data.player.ShimPlayer.shimPlayerTitle
import co.shimm.app.data.player.ShimPlayer.shimPlaylist
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import kotlinx.android.synthetic.main.activity_audio_player.*
import kotlinx.android.synthetic.main.custom_audio_player.*

class AudioPlayerActivity : BaseActivity(), AudioPlayerContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_audio_player

    private lateinit var audioPlayerTitle : TextView
    private lateinit var audioPlayerThumbnail : ImageView
    private lateinit var disposable: Disposable

    override fun initView() {
        presenter = AudioPlayerPresenter(this@AudioPlayerActivity, this)
        presenter.start()

        audio_player_player.player = shimPlayer
        audio_player_player.controllerShowTimeoutMs = 0
        audio_player_player.showController()

        audioPlayerTitle = audio_player_title
        audioPlayerThumbnail = findViewById<View>(R.id.audio_player_thumbnail) as ImageView

        audio_player_counselor_name.text = shimPlayerCounselor?.name
        audio_player_counselor_description.text = shimPlayerCounselor?.about

        audio_player_back_button.setOnClickListener(this)

        disposable = PlayerEventBus.subscribe<PlayerData>()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                audioPlayerTitle.text = it.playerTitle
            }

        updateUI()
        addListener()
    }

    private fun updateUI(){
        if(isViewActive()) {
//            audioPlayerTitle.text = shimPlayerTitle
//            audioPlayerTitle.text = shimPlaylist!![shimPlayer?.currentWindowIndex!!].title

            Glide.with(this).load(shimPlaylist!![shimPlayer?.currentWindowIndex!!].thumbnail).apply(
                bitmapTransform(
                    MultiTransformation<Bitmap>(
                        BlurTransformation(25), ColorFilterTransformation(Color.argb(65, 0, 0, 0))
                    )
                )
            ).error(R.drawable.card_image_sample).into(audio_player_thumbnail)

            Glide.with(this).load(shimPlayerCounselor?.picture)
                .into(audio_player_counselor_thumbnail)
        }

        PlayerEventBus.post(
            PlayerData(
                shimPlaylist!![shimPlayer?.currentWindowIndex!!].title.toString(),
                shimPlaylist!![shimPlayer?.currentWindowIndex!!].thumbnail.toString()
            )
        )
    }

    private fun addListener(){
        val eventListener = object : Player.EventListener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if(playbackState == Player.STATE_READY){
//                    presenter.playNext()
                    updateUI()
                }
                super.onPlayerStateChanged(playWhenReady, playbackState)
            }
        }
        shimPlayer?.addListener(eventListener)
    }

    override lateinit var presenter: AudioPlayerContract.Presenter

    override fun onClick(v: View) {
        when(v.id){
            R.id.audio_player_back_button -> {
                finish()
            }
        }
    }

    override fun isViewActive(): Boolean = checkActive()

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

}
