package co.shimm.app.view.activity.audioplaylist

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
import co.shimm.app.data.player.PlayerData
import co.shimm.app.data.player.PlayerEventBus
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.view.activity.audioplayer.AudioPlayerActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_audio_playlist.*
import kotlinx.android.synthetic.main.card_audio.view.*

class AudioPlaylistActivity : BaseActivity(), AudioPlaylistContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_audio_playlist

    override fun initView() {
        presenter = AudioPlaylistPresenter(this@AudioPlaylistActivity, this)
        presenter.start()

        val recyclerView = audio_playlist_recycler_view
        val recyclerViewAdapter = AudioAdapter(presenter)
        recyclerView.adapter = recyclerViewAdapter

        presenter.initRecyclerViewData(recyclerViewAdapter, intent.getIntExtra("listId", -1))
    }

    override lateinit var presenter: AudioPlaylistContract.Presenter

    override fun onClick(v: View?) { }

    override fun isViewActive(): Boolean = checkActive()

    class AudioAdapter(val presenter: AudioPlaylistContract.Presenter): RecyclerView.Adapter<AudioAdapter.ViewHolder>(){
        private lateinit var shimAudioList : ArrayList<ShimAudio>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_audio,parent,false)
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
                presenter.playAudio(audio, position)
                val intent = Intent(holder.itemView.context, AudioPlayerActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }

        override fun getItemCount() = shimAudioList.size

        fun setItem(audioList: ArrayList<ShimAudio>){
            this.shimAudioList = audioList
        }

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val audioTitle: TextView = view.card_audio_title
            val audioThumbnail: ImageView = view.card_audio_thumbnail
            val audioPlayButton: ImageButton = view.card_audio_play_button
        }
    }
}
