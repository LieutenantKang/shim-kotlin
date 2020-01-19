package co.shimm.app.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.shimm.app.R
import co.shimm.app.base.BaseFragment
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.data.room.entity.ShimAudioPlaylist
import co.shimm.app.data.room.entity.ShimVideo
import co.shimm.app.data.room.entity.ShimVideoPlaylist
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_home_audio.view.*
import kotlinx.android.synthetic.main.card_home_video.view.*

class HomeFragment : BaseFragment(), HomeContract.View {
    override val layoutRes: Int
        get() = R.layout.fragment_home

    private val videoRecyclerView by lazy{
        view?.findViewById<RecyclerView>(R.id.home_video_recycler_view)
    }

    private val audioRecyclerView by lazy{
        view?.findViewById<RecyclerView>(R.id.home_audio_recycler_view)
    }

    override lateinit var presenter: HomeContract.Presenter

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {
        presenter=HomePresenter(this@HomeFragment, requireContext())

        val videoRecyclerViewAdapter = HomeVideoAdapter()
        val audioRecyclerViewAdapter = HomeAudioAdapter()

        videoRecyclerView?.adapter = videoRecyclerViewAdapter
        audioRecyclerView?.adapter = audioRecyclerViewAdapter

        presenter.initRecyclerViewData(videoRecyclerViewAdapter, audioRecyclerViewAdapter)
    }

    override fun isViewActive(): Boolean = checkActive()

    class HomeVideoAdapter: RecyclerView.Adapter<HomeVideoAdapter.ViewHolder>(){
        private lateinit var homeVideoList : ArrayList<ShimVideoPlaylist>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_home_video,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val video = homeVideoList[position]
            Glide.with(holder.itemView.context).load(video.thumbnail)
                .error(R.drawable.card_image_sample).into(holder.homeVideoThumbnail)

            with(holder){
                homeVideoTitle.text = homeVideoList[position].title
            }
        }

        override fun getItemCount() = homeVideoList.size

        fun setItem(homeVideoList : ArrayList<ShimVideoPlaylist>){
            this.homeVideoList = homeVideoList
        }

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val homeVideoTitle : TextView = view.card_home_video_title
            val homeVideoThumbnail : ImageView = view.card_home_video_thumbnail
        }
    }

    class HomeAudioAdapter: RecyclerView.Adapter<HomeAudioAdapter.ViewHolder>(){
        private lateinit var homeAudioList : ArrayList<ShimAudioPlaylist>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_home_audio,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val audio = homeAudioList[position]
            Glide.with(holder.itemView.context).load(audio.thumbnail)
                .error(R.drawable.card_image_sample).into(holder.homeAudioThumbnail)

            with(holder){
                homeAudioTitle.text = homeAudioList[position].title
            }
        }

        override fun getItemCount() = homeAudioList.size

        fun setItem(homeAudioList : ArrayList<ShimAudioPlaylist>){
            this.homeAudioList = homeAudioList
        }

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val homeAudioTitle : TextView = view.card_home_audio_title
            val homeAudioThumbnail : ImageView = view.card_home_audio_thumbnail
        }
    }
}