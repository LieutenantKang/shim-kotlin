package co.shimm.app.view.activity.videoplayer

import android.net.Uri
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.player.ShimPlayerData.shimPlayer
import co.shimm.app.data.player.ShimPlayerData.shimVideoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : BaseActivity(), VideoPlayerContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_video_player

    override fun initView() {
        presenter = VideoPlayerPresenter(this@VideoPlayerActivity, this)
        presenter.start()

        shimVideoPlayer = ExoPlayerFactory.newSimpleInstance(this)
        video_player_view.player = shimVideoPlayer
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        supportActionBar?.hide()


        val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(R.string.app_name.toString()))
            .createMediaSource(Uri.parse(intent.getStringExtra("videoSrc")))
        shimVideoPlayer?.prepare(mediaSource)
        shimVideoPlayer?.playWhenReady = true
        // model 로 옮겨야 함
    }

    override fun onPause() {
        shimVideoPlayer?.stop()
        shimVideoPlayer?.release()
        super.onPause()
    }

    override lateinit var presenter: VideoPlayerContract.Presenter

    override fun onClick(v: View?) {

    }

    override fun isViewActive(): Boolean = checkActive()
}
