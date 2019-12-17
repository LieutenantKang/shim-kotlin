package co.shimm.app.view.activity.main

import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import android.widget.TextView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.player.Player.mainPlayer
import co.shimm.app.data.player.PlayerEventBus
import co.shimm.app.data.player.PlayerData
import co.shimm.app.view.activity.musicplayer.MusicPlayerActivity
import co.shimm.app.view.fragment.home.HomeFragment
import co.shimm.app.view.fragment.music.MusicFragment
import co.shimm.app.view.fragment.video.VideoFragment
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_main_player.*

class MainActivity : BaseActivity(), MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    override val layoutRes: Int
        get() = R.layout.activity_main

    private lateinit var disposable: Disposable
    private lateinit var mainPlayerTitle : TextView
    private lateinit var mainPlayerView : PlayerControlView

    override fun initView() {
        presenter = MainPresenter(this@MainActivity, this)
        presenter.start()

        presenter.fetchData()

        mainPlayerTitle = main_player_title
        mainPlayerView = main_player

        initializePlayer()

        main_player.setOnClickListener(this)

        val bottomNavigationView = findViewById<View>(R.id.main_navigation_view) as BottomNavigationView
        val fragmentHome = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,fragmentHome).commitAllowingStateLoss()
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        disposable = PlayerEventBus.subscribe<PlayerData>()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mainPlayerView.visibility = VISIBLE
                mainPlayerTitle.text = it.playerTitle
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.main_player -> {
                val intent = Intent(this@MainActivity, MusicPlayerActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {
        when(menu.itemId){
            R.id.navigation_home ->{
                val fragmentHome = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,fragmentHome).commitAllowingStateLoss()
            }
            R.id.navigation_shim ->{
                val fragmentShim = VideoFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,fragmentShim).commitAllowingStateLoss()
            }
            R.id.navigation_music ->{
                val fragmentMusic = MusicFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,fragmentMusic).commitAllowingStateLoss()
            }
        }
        return true
    }

    override lateinit var presenter: MainContract.Presenter

    override fun isViewActive(): Boolean = checkActive()

    private fun initializePlayer(){
        if(mainPlayer == null){
            mainPlayer = ExoPlayerFactory.newSimpleInstance(this)
            main_player.player = mainPlayer
            main_player.showTimeoutMs = 0
        }
    }
}