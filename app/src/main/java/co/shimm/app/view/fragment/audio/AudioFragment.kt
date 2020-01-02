package co.shimm.app.view.fragment.audio

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
import co.shimm.app.data.room.entity.ShimAudio
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.card_audio.view.*
import java.util.*

class AudioFragment : BaseFragment(), AudioContract.View {
    override val layoutRes: Int
    get() = R.layout.fragment_audio

    override lateinit var presenter: AudioContract.Presenter
    var recyclerViews = arrayOf<RecyclerView?>(null, null, null, null, null)

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {
        presenter = AudioPresenter(this@AudioFragment, requireContext())

        val pagerAdapter = PagerAdapter(childFragmentManager)
        val pager : ViewPager? = view?.findViewById(R.id.audio_pager)
        pager?.adapter = pagerAdapter
        val tabLayout : TabLayout? = view?.findViewById(R.id.audio_tab)
        tabLayout?.setupWithViewPager(pager)
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position : Int? = tab?.position
                val recyclerView : RecyclerView? = recyclerViews[position!!]
                presenter.updateRecyclerViewData(recyclerView?.adapter as Page.AudioAdapter, position)
            }
        })
    }

    override fun isViewActive(): Boolean = checkActive()

    class Page(private val presenter: AudioContract.Presenter, private val recyclerViews: Array<RecyclerView?>) : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_audio_page, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val args: Bundle? = arguments
            val position: Int? = Objects.requireNonNull(args)?.getInt("position")
            val recyclerViewAdapter = AudioAdapter(presenter)
            val recyclerView : RecyclerView = view.findViewById(R.id.audio_recycler_view)
            recyclerView.adapter = recyclerViewAdapter

            recyclerViews[position!!] = recyclerView
            presenter.updateRecyclerViewData(recyclerViewAdapter,position)
        }

        class AudioAdapter(private val presenter: AudioContract.Presenter) : RecyclerView.Adapter<AudioAdapter.ViewHolder>() {
            private lateinit var shimAudioList : ArrayList<ShimAudio>
            private var tabPosition: Int? = null

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_audio,parent,false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val audio = shimAudioList[position]
                Glide.with(holder.itemView.context).load(audio.thumbnail)
                    .error(R.drawable.card_image_sample).into(holder.audioThumbnail)

                with(holder){
                    audioTitle.text = audio.title
                }

                holder.audioPlayButton.setOnClickListener {
                    presenter.playAudio(audio)
                    PlayerEventBus.post(
                        PlayerData(
                            audio.title.toString(),
                            audio.thumbnail.toString()
                        )
                    )
                }
            }

            override fun getItemCount() = shimAudioList.size

            fun setItem(shimAudioList : ArrayList<ShimAudio>) { this.shimAudioList = shimAudioList }

            fun setTabPosition(tabPosition : Int) { this.tabPosition = tabPosition }

            inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
                val audioTitle : TextView = view.card_audio_title
                val audioThumbnail : ImageView = view.card_audio_thumbnail
                val audioPlayButton : ImageButton = view.card_audio_play_button
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