package co.shimm.app.view.fragment.shim

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import co.shimm.app.R
import co.shimm.app.base.BaseFragment
import co.shimm.app.data.room.Shim
import co.shimm.app.view.activity.main.MainActivity
import co.shimm.app.view.activity.main.MainActivity.Companion.mainPlayerThumbnail
import co.shimm.app.view.activity.main.MainActivity.Companion.mainPlayerTitle
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.card_shim.view.*
import java.util.*

class ShimFragment : BaseFragment(), ShimContract.View {
    override val layoutRes: Int
        get() = R.layout.fragment_shim

    override lateinit var presenter: ShimContract.Presenter
    var recyclerViews = arrayOf<RecyclerView?>(null, null, null, null, null)

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {
        presenter = ShimPresenter(this@ShimFragment, requireContext())

        val pagerAdapter = PagerAdapter(childFragmentManager)
        val pager : ViewPager? = view?.findViewById(R.id.shim_pager)
        pager?.adapter = pagerAdapter
        val tabLayout : TabLayout? = view?.findViewById(R.id.shim_tab)
        tabLayout?.setupWithViewPager(pager)
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position : Int? = tab?.position
                val recyclerView : RecyclerView? = recyclerViews[position!!]
                presenter.updateRecyclerViewData(recyclerView?.adapter as Page.ShimAdapter, position)
            }
        })
    }

    override fun isViewActive(): Boolean = checkActive()

    class Page(private val presenter: ShimContract.Presenter, private val recyclerViews: Array<RecyclerView?>): Fragment(){

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_shim_page, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?){
            val args: Bundle? = arguments
            val position: Int? = Objects.requireNonNull(args)?.getInt("position")
            val recyclerViewAdapter = ShimAdapter()
            val recyclerView: RecyclerView = view.findViewById(R.id.shim_recycler_view)
            recyclerView.adapter = recyclerViewAdapter

            recyclerViews[position!!] = recyclerView
            presenter.updateRecyclerViewData(recyclerViewAdapter, position)
        }

        class ShimAdapter() : RecyclerView.Adapter<ShimAdapter.ViewHolder>(){
            private lateinit var shimList : ArrayList<Shim>
            private var tabPosition : Int? = null

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_shim,parent,false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val shim = shimList[position]
                Glide.with(holder.itemView.context).load(shim.thumbnail)
                    .error(R.drawable.card_image_sample).into(holder.shimThumbnail)

                with(holder){
                    shimTitle.text = shim.title
                }
                holder.shimPlayButton.setOnClickListener{
                    val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(R.string.app_name.toString()))
                        .createMediaSource(Uri.parse(shim.src))
                    MainActivity.mainPlayer?.prepare(mediaSource)
                    MainActivity.mainPlayer?.playWhenReady = true
                    mainPlayerThumbnail = shim.src
                    mainPlayerTitle = shim.title
                }
            }

            override fun getItemCount() = shimList.size

            fun setItem(shimList : ArrayList<Shim>){ this.shimList = shimList }

            fun setTabPosition(tabPosition : Int) { this.tabPosition = tabPosition }

            inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
                val shimTitle : TextView = view.card_shim_title
                val shimThumbnail : ImageView = view.card_shim_thumbnail
                val shimPlayButton : ImageButton = view.card_shim_play_button
            }
        }
    }

    inner class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        override fun getItem(position: Int): Fragment {
            val page: Fragment = Page(presenter, recyclerViews)
            val args = Bundle()
            args.putInt("position", position)
            page.arguments = args
            return page
        }

        override fun getCount(): Int { return 5 }

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> "All"
                1 -> "Favorite"
                2 -> "ASMR"
                3 -> "Relax"
                4 -> "Focus"
                else -> "Unknown"
            }
        }
    }
}