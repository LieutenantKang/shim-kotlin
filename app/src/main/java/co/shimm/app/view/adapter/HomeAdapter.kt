package co.shimm.app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.shimm.app.R
import co.shimm.app.data.room.Shim
import kotlinx.android.synthetic.main.card_home.view.*

class HomeAdapter(val context: Context) : AdapterContract.View, AdapterContract.Model, RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private lateinit var homeList : ArrayList<Shim>

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        homeList[position].let{item->
            with(holder){
                homeTitle.text = item.title
            }
        }

        holder.homePlayButton.setOnClickListener {
            // Play the music
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_home,parent,false)
        return HomeViewHolder(view)
    }

    override fun getItemCount() = homeList.size

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun clearItem(){
        homeList.clear()
    }

    override fun addItems(list: ArrayList<Shim>) {
        this.homeList = list
    }

    inner class HomeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val homeTitle : TextView = view.card_home_title
        val homePlayButton : ImageButton = view.card_home_play_button
    }
}