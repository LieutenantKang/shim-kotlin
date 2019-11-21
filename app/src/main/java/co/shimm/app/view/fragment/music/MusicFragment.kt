package co.shimm.app.view.fragment.music

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import co.shimm.app.R
import co.shimm.app.base.BaseFragment
import co.shimm.app.data.room.Music
import co.shimm.app.data.room.MusicDao
import co.shimm.app.data.room.ShimDatabase
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.card_music.view.*
import java.util.*
import kotlin.collections.ArrayList

class MusicFragment : BaseFragment(), MusicContract.View {
    override val layoutRes: Int
    get() = R.layout.fragment_music

    companion object{
        private var recyclerViews = arrayOf<RecyclerView?>(null, null, null, null, null)
    }

    override lateinit var presenter: MusicContract.Presenter

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {
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
                Page.RecyclerViewUpdater(recyclerView?.adapter as Page.MusicAdapter).execute(context, position)
            }
        })
    }

    override fun isViewActive(): Boolean = checkActive()

    class Page : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_music_page, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val args: Bundle? = arguments
            val position: Int? = Objects.requireNonNull(args)?.getInt("position")
            val recyclerViewAdapter = MusicAdapter()
            val recyclerView : RecyclerView = view.findViewById(R.id.music_recycler_view)
            recyclerView.adapter = recyclerViewAdapter

            recyclerViews[position!!] = recyclerView
            RecyclerViewUpdater(recyclerViewAdapter).execute(requireContext(), position)
        }

        class RecyclerViewUpdater(private val adapter : MusicAdapter): AsyncTask<Any, Void, Void>() {
            override fun doInBackground(vararg params: Any): Void? {
                val dao : MusicDao = ShimDatabase.getInstance(params[0] as Context).musicDao
                val position = params[1] as Int
                when (position){
                    0 -> adapter.setItem(dao.getAll() as ArrayList<Music>)
                    1 -> adapter.setItem(dao.findByCategory("favorite") as ArrayList<Music>)
                    2 -> adapter.setItem(dao.findByCategory("asmr") as ArrayList<Music>)
                    3 -> adapter.setItem(dao.findByCategory("relax") as ArrayList<Music>)
                    4 -> adapter.setItem(dao.findByCategory("focus") as ArrayList<Music>)
                }
                adapter.setTabPosition(position)
                return null
            }

            override fun onPostExecute(result: Void?) {
                adapter.notifyDataSetChanged()
            }
        }

        inner class MusicAdapter : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
            private lateinit var musicList : ArrayList<Music>
            private var tabPosition: Int? = null

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_music,parent,false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                musicList[position].let{item->
                    with(holder){
                        musicTitle.text = item.title
                    }
                }

                holder.musicPlayButton.setOnClickListener {
                    // Play the music
                }
            }

            override fun getItemCount() = musicList.size

            fun setItem(musicList : ArrayList<Music>) {
                this.musicList = musicList
            }

            fun setTabPosition(tabPosition : Int) { this.tabPosition = tabPosition }

            inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
                val musicTitle : TextView = view.card_music_title
                val musicPlayButton : ImageButton = view.card_music_play_button
            }
        }
    }

    inner class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            val page: Fragment = Page()
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