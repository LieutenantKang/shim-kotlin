package co.shimm.app.view.activity.main

import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.PlayingMusic
import co.shimm.app.databinding.CustomPlayerControlBinding
import co.shimm.app.view.activity.player.PlayerActivity
import co.shimm.app.view.fragment.home.HomeFragment
import co.shimm.app.view.fragment.music.MusicFragment
import co.shimm.app.view.fragment.shim.ShimFragment
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    override val layoutRes: Int
        get() = R.layout.activity_main

    lateinit var binding: CustomPlayerControlBinding

    companion object{
        var mainPlayer: SimpleExoPlayer? = null
        var mainPlayerThumbnail : String? = null
        var mainPlayerTitle : String ? = null
    }

    override fun initView() {
        presenter = MainPresenter(this@MainActivity, this)
        presenter.start()

        presenter.fetchData()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.music = PlayingMusic("Hi", "Hi")

        initializePlayer()

        main_player.setOnClickListener(this)

        val bottomNavigationView = findViewById<View>(R.id.main_navigation_view) as BottomNavigationView
        val fragmentHome = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,fragmentHome).commitAllowingStateLoss()
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.main_player -> {
                val intent = Intent(this@MainActivity, PlayerActivity::class.java)
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
                val fragmentShim = ShimFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout,fragmentShim).commitAllowingStateLoss()
            }
            R.id.navigation_music ->{
                val fragmentMusic = MusicFragment(binding)
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
//            mainPlayer = ExoPlayerFactory.newSimpleInstance(this)
//            main_player.player = mainPlayer
//            main_player.showTimeoutMs = 0
        }
    }
}
