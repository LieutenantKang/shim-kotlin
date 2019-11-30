package co.shimm.app.view.activity.main

import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import android.widget.TextView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.view.fragment.home.HomeFragment
import co.shimm.app.view.fragment.music.MusicFragment
import co.shimm.app.view.fragment.shim.ShimFragment
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_player_control.*

class MainActivity : BaseActivity(), MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener{
    override val layoutRes: Int
        get() = R.layout.activity_main

    companion object{
        var mainPlayer: SimpleExoPlayer? = null
        var customTitle : TextView? = null
        var mainPlayerView : PlayerControlView? = null

        fun changeTitle(string : String){
            customTitle?.text = string
            if(mainPlayerView?.isVisible!=true){
                mainPlayerView?.visibility=VISIBLE
            }
        }
    }

    override fun initView() {
        presenter = MainPresenter(this@MainActivity, this)
        presenter.start()

        presenter.fetchData()

        customTitle = custom_player_title
        mainPlayerView = main_player

        initializePlayer()

        val bottomNavigationView = findViewById<View>(R.id.main_navigation_view) as BottomNavigationView
        val fragmentHome = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,fragmentHome).commitAllowingStateLoss()
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {
        when(menu.itemId){
            R.id.navigation_home ->{
                val fragmentHome = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,fragmentHome).commitAllowingStateLoss()
            }
            R.id.navigation_shim ->{
                val fragmentShim = ShimFragment()
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

    fun initializePlayer(){
        if(mainPlayer == null){
            mainPlayer = ExoPlayerFactory.newSimpleInstance(this)
            main_player.player = mainPlayer
            main_player.showTimeoutMs = 0
        }
    }
}
