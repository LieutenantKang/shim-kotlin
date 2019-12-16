package co.shimm.app.view.activity.detail

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.player.Player.mainPlayer
import co.shimm.app.data.player.Player.mainPlayerThumbnail
import co.shimm.app.data.player.Player.mainPlayerTitle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.custom_player.*

class DetailActivity : BaseActivity(), DetailContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_detail

    private lateinit var customTestTitle : TextView
    private lateinit var customTestThumbnail : ImageView

    override fun initView() {
        presenter = DetailPresenter(this@DetailActivity, this)
        presenter.start()

        player_player.player = mainPlayer

        customTestTitle = player_title
        customTestThumbnail = findViewById<View>(R.id.player_thumbnail) as ImageView

        customTestTitle.text = mainPlayerTitle
        Glide.with(this).load(mainPlayerThumbnail)
            .centerCrop().into(customTestThumbnail)
    }

    override lateinit var presenter: DetailContract.Presenter

    override fun onClick(v: View) {

    }

    override fun isViewActive(): Boolean = checkActive()
}
