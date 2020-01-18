package co.shimm.app.view.fragment.video

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import co.shimm.app.R
import co.shimm.app.base.BaseFragment
import co.shimm.app.data.room.entity.ShimVideoPlaylist
import co.shimm.app.view.activity.videoplayer.VideoPlayerActivity
import co.shimm.app.view.activity.videoplaylist.VideoPlaylistActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.card_video_playlist.view.*
import java.util.*

class VideoFragment : BaseFragment(), VideoContract.View {
    override val layoutRes: Int
        get() = R.layout.fragment_video

    override lateinit var presenter: VideoContract.Presenter
    var recyclerViews = arrayOf<RecyclerView?>(null)

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {
        presenter = VideoPresenter(this@VideoFragment, requireContext())

        val pagerAdapter = PagerAdapter(childFragmentManager)
        val pager : ViewPager? = view?.findViewById(R.id.video_pager)
        pager?.adapter = pagerAdapter
        val tabLayout : TabLayout? = view?.findViewById(R.id.video_tab)
        tabLayout?.setupWithViewPager(pager)
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
//                val position : Int? = tab?.position
//                val recyclerView : RecyclerView? = recyclerViews[position!!]
//                presenter.updateRecyclerViewData(recyclerView?.adapter as Page.VideoPlaylistAdapter, position)
            }
        })
    }

    override fun isViewActive(): Boolean = checkActive()

    class Page(private val presenter: VideoContract.Presenter, private val recyclerViews: Array<RecyclerView?>): Fragment(){

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_video_page, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?){
            val args: Bundle? = arguments
            val position: Int? = Objects.requireNonNull(args)?.getInt("position")
            val recyclerViewAdapter = VideoPlaylistAdapter()
            val recyclerView: RecyclerView = view.findViewById(R.id.video_recycler_view)
            recyclerView.adapter = recyclerViewAdapter

            recyclerViews[position!!] = recyclerView
            presenter.updateRecyclerViewData(recyclerViewAdapter, position)
        }

        class VideoPlaylistAdapter : RecyclerView.Adapter<VideoPlaylistAdapter.ViewHolder>(){
            private lateinit var shimVideoPlaylist : ArrayList<ShimVideoPlaylist>
            private var tabPosition : Int? = null

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_video_playlist,parent,false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val videoPlaylist = shimVideoPlaylist[position]
                Glide.with(holder.itemView.context).load(videoPlaylist.thumbnail)
                    .error(R.drawable.card_image_sample).into(holder.videoPlaylistThumbnail)

                with(holder){
                    videoPlaylistTitle.text = videoPlaylist.title
                }
                holder.videoPlaylistLayout.setOnClickListener{
                    val intent = Intent(holder.itemView.context, VideoPlaylistActivity::class.java)
                    intent.putExtra("listId", videoPlaylist.id)
                    holder.itemView.context.startActivity(intent)
                }
            }

            override fun getItemCount() = shimVideoPlaylist.size

            fun setItem(shimVideoPlaylist : ArrayList<ShimVideoPlaylist>){ this.shimVideoPlaylist = shimVideoPlaylist }

            fun setTabPosition(tabPosition : Int) { this.tabPosition = tabPosition }

            inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
                val videoPlaylistTitle : TextView = view.card_video_playlist_title
                val videoPlaylistThumbnail : ImageView = view.card_video_playlist_thumbnail
                val videoPlaylistLayout : ConstraintLayout = view.card_video_playlist_layout
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

        override fun getCount(): Int { return 1 }

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> "All"
                else -> "Unknown"
            }
        }
    }
}