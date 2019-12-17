package co.shimm.app.view.activity.musicplayer

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.player.Player.mainPlayer
import co.shimm.app.data.player.Player.playerThumbnail
import co.shimm.app.data.player.Player.playerTitle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_music_player.*
import kotlinx.android.synthetic.main.custom_music_player.*

class MusicPlayerActivity : BaseActivity(), MusicPlayerContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_music_player

    private lateinit var musicPlayerTitle : TextView
    private lateinit var musicPlayerThumbnail : ImageView

    override fun initView() {
        presenter = MusicPlayerPresenter(this@MusicPlayerActivity, this)
        presenter.start()

        music_player_player.player = mainPlayer
        music_player_player.controllerShowTimeoutMs = 0

        musicPlayerTitle = music_player_title
        musicPlayerThumbnail = findViewById<View>(R.id.music_player_thumbnail) as ImageView

        musicPlayerTitle.text = playerTitle
        Glide.with(applicationContext).load(playerThumbnail)
            .centerCrop().into(musicPlayerThumbnail)
    }

    override lateinit var presenter: MusicPlayerContract.Presenter

    override fun onClick(v: View) {

    }

    override fun isViewActive(): Boolean = checkActive()
}
