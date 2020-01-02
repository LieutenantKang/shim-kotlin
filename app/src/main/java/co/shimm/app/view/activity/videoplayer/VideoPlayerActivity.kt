package co.shimm.app.view.activity.videoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseActivity

class VideoPlayerActivity : BaseActivity(), VideoPlayerContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_video_player

    override fun initView() {
        presenter = VideoPlayerPresenter(this@VideoPlayerActivity, this)
        presenter.start()

    }

    override lateinit var presenter: VideoPlayerContract.Presenter

    override fun onClick(v: View?) {

    }

    override fun isViewActive(): Boolean = checkActive()
}
