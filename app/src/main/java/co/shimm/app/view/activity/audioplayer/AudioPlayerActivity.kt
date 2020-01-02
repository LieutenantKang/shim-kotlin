package co.shimm.app.view.activity.audioplayer

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.player.ShimPlayer.shimPlayer
import co.shimm.app.data.player.ShimPlayer.shimPlayerThumbnail
import co.shimm.app.data.player.ShimPlayer.shimPlayerTitle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_audio_player.*
import kotlinx.android.synthetic.main.custom_audio_player.*

class AudioPlayerActivity : BaseActivity(), AudioPlayerContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_audio_player

    private lateinit var audioPlayerTitle : TextView
    private lateinit var audioPlayerThumbnail : ImageView

    override fun initView() {
        presenter = AudioPlayerPresenter(this@AudioPlayerActivity, this)
        presenter.start()

        audio_player_player.player = shimPlayer
        audio_player_player.controllerShowTimeoutMs = 0

        audioPlayerTitle = audio_player_title
        audioPlayerThumbnail = findViewById<View>(R.id.audio_player_thumbnail) as ImageView

        audioPlayerTitle.text = shimPlayerTitle
        Glide.with(applicationContext).load(shimPlayerThumbnail)
            .centerCrop().into(audioPlayerThumbnail)
    }

    override lateinit var presenter: AudioPlayerContract.Presenter

    override fun onClick(v: View) {

    }

    override fun isViewActive(): Boolean = checkActive()
}
