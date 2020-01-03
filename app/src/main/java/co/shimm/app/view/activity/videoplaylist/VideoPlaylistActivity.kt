package co.shimm.app.view.activity.videoplaylist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.room.entity.ShimVideo
import co.shimm.app.view.activity.videoplayer.VideoPlayerActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_video_playlist.*
import kotlinx.android.synthetic.main.card_video.view.*

class VideoPlaylistActivity : BaseActivity(), VideoPlaylistContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_video_playlist

    override fun initView() {
        presenter = VideoPlaylistPresenter(this@VideoPlaylistActivity, this)
        presenter.start()

        val recyclerView = video_playlist_recycler_view
        val recyclerViewAdapter = VideoAdapter()
        recyclerView.adapter = recyclerViewAdapter

        presenter.initRecyclerViewData(recyclerViewAdapter, intent.getIntExtra("listId", -1))
    }

    override lateinit var presenter: VideoPlaylistContract.Presenter

    override fun onClick(v: View?) {

    }

    override fun isViewActive(): Boolean = checkActive()

    class VideoAdapter: RecyclerView.Adapter<VideoAdapter.ViewHolder>(){
        private lateinit var shimVideoList : ArrayList<ShimVideo>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_video,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val video = shimVideoList[position]
            Glide.with(holder.itemView.context).load(video.thumbnail)
                .error(R.drawable.card_image_sample).into(holder.videoThumbnail)
            with(holder){
                videoTitle.text = video.title
            }
            holder.videoPlayButton.setOnClickListener {
                val intent = Intent(holder.itemView.context, VideoPlayerActivity::class.java)
                intent.putExtra("videoSrc", video.src)
                holder.itemView.context.startActivity(intent)
            }
        }

        override fun getItemCount() = shimVideoList.size

        fun setItem(videoList: ArrayList<ShimVideo>){
            this.shimVideoList = videoList
        }

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val videoTitle: TextView = view.card_video_title
            val videoThumbnail: ImageView = view.card_video_thumbnail
            val videoPlayButton: ImageButton = view.card_video_play_button
        }
    }
}
