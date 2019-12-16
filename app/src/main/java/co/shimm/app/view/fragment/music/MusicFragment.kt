package co.shimm.app.view.fragment.music

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
import co.shimm.app.data.player.PlayerEventBus
import co.shimm.app.data.player.PlayerData
import co.shimm.app.data.room.Music
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.card_music.view.*
import java.util.*

class MusicFragment : BaseFragment(), MusicContract.View {
    override val layoutRes: Int
    get() = R.layout.fragment_music

    override lateinit var presenter: MusicContract.Presenter
    var recyclerViews = arrayOf<RecyclerView?>(null, null, null, null, null)

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {
        presenter = MusicPresenter(this@MusicFragment, requireContext())

        val pagerAdapter = PagerAdapter(childFragmentManager)
        val pager : ViewPager? = view?.findViewById(R.id.music_pager)
        pager?.adapter = pagerAdapter
        val tabLayout : TabLayout? = view?.findViewById(R.id.music_tab)
        tabLayout?.setupWithViewPager(pager)
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position : Int? = tab?.position
                val recyclerView : RecyclerView? = recyclerViews[position!!]
                presenter.updateRecyclerViewData(recyclerView?.adapter as Page.MusicAdapter, position)
            }
        })
    }

    override fun isViewActive(): Boolean = checkActive()

    class Page(private val presenter: MusicContract.Presenter, private val recyclerViews: Array<RecyclerView?>) : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_music_page, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val args: Bundle? = arguments
            val position: Int? = Objects.requireNonNull(args)?.getInt("position")
            val recyclerViewAdapter = MusicAdapter(presenter)
            val recyclerView : RecyclerView = view.findViewById(R.id.music_recycler_view)
            recyclerView.adapter = recyclerViewAdapter

            recyclerViews[position!!] = recyclerView
            presenter.updateRecyclerViewData(recyclerViewAdapter,position)
        }

        class MusicAdapter(private val presenter: MusicContract.Presenter) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
            private lateinit var musicList : ArrayList<Music>
            private var tabPosition: Int? = null

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_music,parent,false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val music = musicList[position]
                Glide.with(holder.itemView.context).load(music.thumbnail)
                    .error(R.drawable.card_image_sample).into(holder.musicThumbnail)

                with(holder){
                    musicTitle.text = music.title
                }

                holder.musicPlayButton.setOnClickListener {
                    presenter.playMusic(music)
                    PlayerEventBus.post(
                        PlayerData(
                            music.title.toString(),
                            music.thumbnail.toString()
                        )
                    )
                }
            }

            override fun getItemCount() = musicList.size

            fun setItem(musicList : ArrayList<Music>) { this.musicList = musicList }

            fun setTabPosition(tabPosition : Int) { this.tabPosition = tabPosition }

            inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
                val musicTitle : TextView = view.card_music_title
                val musicThumbnail : ImageView = view.card_music_thumbnail
                val musicPlayButton : ImageButton = view.card_music_play_button
            }
        }
    }

    inner class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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