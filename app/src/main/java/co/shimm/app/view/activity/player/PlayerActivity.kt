package co.shimm.app.view.activity.player

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.view.activity.main.MainActivity.Companion.mainPlayer
import co.shimm.app.view.activity.main.MainActivity.Companion.mainPlayerThumbnail
import co.shimm.app.view.activity.main.MainActivity.Companion.mainPlayerTitle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.custom_player.*

class PlayerActivity : BaseActivity(), PlayerContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_player

    lateinit var customTestTitle : TextView
    lateinit var customTestThumbnail : ImageView

    override fun initView() {
        presenter = PlayerPresenter(this@PlayerActivity, this)
        presenter.start()

        val playerTitleView = findViewById<View>(R.id.player_title) as TextView?
        val playerThumbnail = findViewById<View>(R.id.player_thumbnail) as ImageView?
        player_player.player = mainPlayer

        customTestTitle = player_title
        customTestThumbnail = findViewById<View>(R.id.player_thumbnail) as ImageView

        customTestTitle.text = mainPlayerTitle
        Glide.with(this).load(mainPlayerThumbnail)
            .centerCrop().into(customTestThumbnail)
    }

    override lateinit var presenter: PlayerContract.Presenter

    override fun onClick(v: View) {

    }

    override fun isViewActive(): Boolean = checkActive()
}
