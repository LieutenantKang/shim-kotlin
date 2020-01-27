package co.shimm.app.view.activity.videoplaylist

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.player.HidePlayer
import co.shimm.app.data.player.PlayerEventBus
import co.shimm.app.data.room.entity.ShimVideo
import co.shimm.app.view.activity.videoplayer.VideoPlayerActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import kotlinx.android.synthetic.main.activity_video_playlist.*
import kotlinx.android.synthetic.main.card_video.view.*

class VideoPlaylistActivity : BaseActivity(), VideoPlaylistContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_video_playlist

    override fun initView() {
        presenter = VideoPlaylistPresenter(this@VideoPlaylistActivity, this)
        presenter.start()

        val shimVideoPlaylist = presenter.getVideoPlaylist(intent.getIntExtra("listId", -1))

        val recyclerView = video_playlist_recycler_view
        val recyclerViewAdapter = VideoAdapter()
        recyclerView.adapter = recyclerViewAdapter

        Glide.with(this).load(shimVideoPlaylist?.thumbnail).apply(RequestOptions.bitmapTransform(MultiTransformation<Bitmap>(
            BlurTransformation(25), ColorFilterTransformation(Color.argb(65, 0, 0, 0))
        ))).error(R.drawable.card_image_sample).into(video_playlist_thumbnail)

        video_playlist_title.text = shimVideoPlaylist?.title
        video_playlist_description.text = shimVideoPlaylist?.description
        video_playlist_counselor_name.text = presenter.getCounselor(shimVideoPlaylist?.counselorId!!).name

        video_playlist_back_button.setOnClickListener(this)

        presenter.initRecyclerViewData(recyclerViewAdapter, intent.getIntExtra("listId", -1))
    }

    override lateinit var presenter: VideoPlaylistContract.Presenter

    override fun onClick(v: View) {
        when(v.id){
            R.id.video_playlist_back_button -> finish()
        }
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
            with(holder){
                videoTitle.text = video.title
                videoDuration.text = getDurationText(video.duration!!)
            }
            holder.videoLayout.setOnClickListener {
                PlayerEventBus.post(HidePlayer(true))

                val intent = Intent(holder.itemView.context, VideoPlayerActivity::class.java)
                intent.putExtra("videoSrc", video.src)
                holder.itemView.context.startActivity(intent)
            }
        }

        private fun getDurationText(duration: Int): String{
            return if((duration%60>=10)){
                (duration/60).toString()+":"+(duration%60).toString()
            }else{
                (duration/60).toString()+":0"+(duration%60).toString()
            }
        }

        override fun getItemCount() = shimVideoList.size

        fun setItem(videoList: ArrayList<ShimVideo>){
            this.shimVideoList = videoList
        }

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val videoTitle: TextView = view.card_video_title
            val videoDuration: TextView = view.card_video_duration
            val videoLayout: ConstraintLayout = view.card_video_layout
        }
    }
}
