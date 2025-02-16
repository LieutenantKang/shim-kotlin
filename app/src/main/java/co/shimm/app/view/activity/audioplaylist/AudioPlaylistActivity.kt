package co.shimm.app.view.activity.audioplaylist

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.Color
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.player.ShimPlayerData.shimPlayerCounselor
import co.shimm.app.data.player.ShimPlayerData.shimPlayerThumbnail
import co.shimm.app.data.player.ShimPlayerData.shimPlayerTitle
import co.shimm.app.data.player.ShimPlayerService
import co.shimm.app.data.room.entity.ShimAudio
import co.shimm.app.data.room.entity.ShimAudioPlaylist
import co.shimm.app.view.activity.audioplayer.AudioPlayerActivity
import co.shimm.app.view.activity.main.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import kotlinx.android.synthetic.main.activity_audio_playlist.*
import kotlinx.android.synthetic.main.card_audio.view.*

class AudioPlaylistActivity : BaseActivity(), AudioPlaylistContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_audio_playlist

    override fun initView() {
        presenter = AudioPlaylistPresenter(this@AudioPlaylistActivity, this)
        presenter.start()

        val shimAudioPlaylist = presenter.getAudioPlaylist(intent.getIntExtra("listId",-1))

        val recyclerView = audio_playlist_recycler_view
        val recyclerViewAdapter = AudioAdapter(presenter, shimAudioPlaylist!!)
        recyclerView.adapter = recyclerViewAdapter

        Glide.with(this).load(shimAudioPlaylist.thumbnail).apply(bitmapTransform(MultiTransformation<Bitmap>(
            BlurTransformation(25), ColorFilterTransformation(Color.argb(65,0,0,0))
        ))).error(R.drawable.card_image_sample).into(audio_playlist_thumbnail)

        audio_playlist_title.text = shimAudioPlaylist.title
        audio_playlist_description.text = shimAudioPlaylist.description
        audio_playlist_counselor_name.text = presenter.getCounselor(shimAudioPlaylist.counselorId!!).name

        audio_playlist_back_button.setOnClickListener(this)

        presenter.initRecyclerViewData(recyclerViewAdapter, intent.getIntExtra("listId", -1))
    }

    override lateinit var presenter: AudioPlaylistContract.Presenter

    override fun onClick(v: View) {
        when(v.id){
            R.id.audio_playlist_back_button -> {
                finish()
            }
        }
    }


    override fun isViewActive(): Boolean = checkActive()

    class AudioAdapter(val presenter: AudioPlaylistContract.Presenter, private val shimAudioPlaylist: ShimAudioPlaylist): RecyclerView.Adapter<AudioAdapter.ViewHolder>(){
        private lateinit var shimAudioList : ArrayList<ShimAudio>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_audio,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val audio = shimAudioList[position]
            with(holder){
                audioTitle.text = audio.title
                audioDuration.text = getDurationText(audio.duration!!)
            }
            holder.audioLayout.setOnClickListener {
                presenter.playAudio(audio, position)
                shimPlayerTitle = audio.title
                shimPlayerThumbnail = shimAudioPlaylist.thumbnail
                val intent = Intent(holder.itemView.context, AudioPlayerActivity::class.java)
                shimPlayerCounselor = presenter.getCounselor(shimAudioPlaylist.counselorId!!)
                holder.itemView.context.startActivity(intent)
//                MainActivity.shimService.playAudio(position)

            }
        }

        override fun getItemCount() = shimAudioList.size

        private fun getDurationText(duration: Int): String{
            return if((duration%60>=10)){
                (duration/60).toString()+":"+(duration%60).toString()
            }else{
                (duration/60).toString()+":0"+(duration%60).toString()
            }
        }

        fun setItem(audioList: ArrayList<ShimAudio>){
            this.shimAudioList = audioList
        }

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val audioTitle: TextView = view.card_audio_title
            val audioDuration: TextView = view.card_audio_duration
            val audioLayout: ConstraintLayout = view.card_audio_layout
        }
    }
}
