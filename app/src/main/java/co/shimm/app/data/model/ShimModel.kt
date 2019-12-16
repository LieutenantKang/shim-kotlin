package co.shimm.app.data.model

import android.content.Context
import android.net.Uri
import co.shimm.app.R
import co.shimm.app.data.player.Player
import co.shimm.app.data.room.Shim
import co.shimm.app.data.room.ShimDao
import co.shimm.app.data.room.ShimDatabase
import co.shimm.app.view.fragment.shim.ShimFragment
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class ShimModel(context: Context) {
    private val shimDao : ShimDao = ShimDatabase.getInstance(context).shimDao

    fun updateRecyclerViewData(adapter: ShimFragment.Page.ShimAdapter, position: Int){
        lateinit var updateThread : Thread
        when(position){
            0 -> updateThread = Thread { adapter.setItem(shimDao.getAll() as ArrayList<Shim>)}
            1 -> updateThread = Thread { adapter.setItem(shimDao.findByCategory(0) as ArrayList<Shim>)}
            2 -> updateThread = Thread { adapter.setItem(shimDao.findByCategory(1) as ArrayList<Shim>)}
            3 -> updateThread = Thread { adapter.setItem(shimDao.findByCategory(2) as ArrayList<Shim>)}
            4 -> updateThread = Thread { adapter.setItem(shimDao.findByCategory(2) as ArrayList<Shim>)}
        }

        updateThread.start()

        try{
            updateThread.join()
        }catch (e: InterruptedException){
            e.printStackTrace()
        }

        adapter.setTabPosition(position)
    }

    fun playShim(shim: Shim){
        val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(R.string.app_name.toString()))
            .createMediaSource(Uri.parse(shim.src))
        Player.mainPlayer?.prepare(mediaSource)
        Player.mainPlayer?.playWhenReady = true
        Player.mainPlayerThumbnail = shim.src
        Player.mainPlayerTitle = shim.title
    }
}