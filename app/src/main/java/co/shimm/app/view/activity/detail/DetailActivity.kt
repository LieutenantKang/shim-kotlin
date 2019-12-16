package co.shimm.app.view.activity.detail

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.player.Player.mainPlayer
import co.shimm.app.data.player.Player.playerThumbnail
import co.shimm.app.data.player.Player.playerTitle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.custom_detail_player.*

class DetailActivity : BaseActivity(), DetailContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_detail

    private lateinit var detailPlayerTitle : TextView
    private lateinit var detailPlayerThumbnail : ImageView

    override fun initView() {
        presenter = DetailPresenter(this@DetailActivity, this)
        presenter.start()

        detail_player.player = mainPlayer
        detail_player.controllerShowTimeoutMs = 0

        detailPlayerTitle = detail_player_title
        detailPlayerThumbnail = findViewById<View>(R.id.detail_player_thumbnail) as ImageView

        detailPlayerTitle.text = playerTitle
        Glide.with(applicationContext).load(playerThumbnail)
            .centerCrop().into(detailPlayerThumbnail)
    }

    override lateinit var presenter: DetailContract.Presenter

    override fun onClick(v: View) {

    }

    override fun isViewActive(): Boolean = checkActive()
}
