package co.shimm.app.view.activity.main

import android.view.MenuItem
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.view.fragment.home.HomeFragment
import co.shimm.app.view.fragment.music.MusicFragment
import co.shimm.app.view.fragment.shim.ShimFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity(), MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener{
    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun initView() {
        presenter = MainPresenter(this@MainActivity, this)
        presenter.start()

        presenter.fetchData()

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
}
