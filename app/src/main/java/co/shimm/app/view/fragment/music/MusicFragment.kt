package co.shimm.app.view.fragment.music

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import co.shimm.app.R
import co.shimm.app.base.BaseFragment
import co.shimm.app.data.room.Music
import kotlinx.android.synthetic.main.card_music.view.*
import java.util.*
import kotlin.collections.ArrayList

class MusicFragment : BaseFragment(), MusicContract.View {
    override val layoutRes: Int
    get() = R.layout.fragment_music

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {

    }

    override lateinit var presenter: MusicContract.Presenter
    var recyclerViews = arrayOf<RecyclerView?>(null, null, null, null, null)

    override fun isViewActive(): Boolean = checkActive()

    inner class Page : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_music_page, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val args: Bundle? = arguments
            val position: Int = Objects.requireNonNull(args).getInt("position")
            val recyclerViewAdapter = MusicAdapter()
            val recyclerView : RecyclerView = view.findViewById(R.id.music_recycler_view)
            recyclerView.adapter = recyclerViewAdapter
            recyclerViews[position] = recyclerView
            // fetch adapter.items & tabPosition
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

            fun setItem(musicList : ArrayList<Music>) { this.musicList = musicList }

            fun setTabPosition(tabPosition : Int) { this.tabPosition = tabPosition }

            inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
                val musicTitle : TextView = view.card_music_title
                val musicPlayButton : ImageButton = view.card_music_play_button
            }
        }

    }
}