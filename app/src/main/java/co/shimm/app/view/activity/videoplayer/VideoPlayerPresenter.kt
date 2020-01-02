package co.shimm.app.view.activity.videoplayer

import android.content.Context

class VideoPlayerPresenter(private val view: VideoPlayerContract.View, context: Context): VideoPlayerContract.Presenter {
    override fun start(){
        view.presenter = this
    }
}