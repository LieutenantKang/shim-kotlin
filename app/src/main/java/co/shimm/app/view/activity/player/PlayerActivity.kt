package co.shimm.app.view.activity.player

import android.net.Uri
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : BaseActivity(), PlayerContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_player

    override fun initView() {
        presenter = PlayerPresenter(this@PlayerActivity, this)
        presenter.start()

        var musicPlayer = ExoPlayerFactory.newSimpleInstance(this)
        music_player.player = musicPlayer

        val uriList: ArrayList<Uri> = arrayListOf()
        uriList.add(Uri.parse("https://s3.ap-northeast-2.amazonaws.com/shim-music/White_River.mp3"))
        uriList.add(Uri.parse("https://s3.ap-northeast-2.amazonaws.com/shim-music/Minyo_San_Kyoku.mp3"))
        val mediaSource : MediaSource = buildMediaSource(uriList)
        musicPlayer.playWhenReady = true
        musicPlayer.seekTo(0, 0)
        musicPlayer.prepare(mediaSource, false, false)
    }

    fun buildMediaSource(uriList : ArrayList<Uri>) : MediaSource{
        val dataSourceFactory : DataSource.Factory = DefaultDataSourceFactory(this, "exo-player")
        val mediaSourceFactory : ProgressiveMediaSource.Factory = ProgressiveMediaSource.Factory(dataSourceFactory)
        return ConcatenatingMediaSource(mediaSourceFactory.createMediaSource(uriList[0]), mediaSourceFactory.createMediaSource(uriList[1]))
    }

    override lateinit var presenter: PlayerContract.Presenter

    override fun onClick(p0: View?) {

    }

    override fun isViewActive(): Boolean = checkActive()
}
