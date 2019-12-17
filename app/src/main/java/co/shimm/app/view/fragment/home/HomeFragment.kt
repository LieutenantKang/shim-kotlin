package co.shimm.app.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.shimm.app.R
import co.shimm.app.base.BaseFragment
import co.shimm.app.data.room.Video
import kotlinx.android.synthetic.main.card_home.view.*

class HomeFragment : BaseFragment(), HomeContract.View {
    override val layoutRes: Int
        get() = R.layout.fragment_home

    private val recyclerView by lazy{
        view?.findViewById<RecyclerView>(R.id.home_recycler_view)
    }

    override lateinit var presenter: HomeContract.Presenter

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {
        presenter=HomePresenter(this@HomeFragment, requireContext())

        val recyclerViewAdapter = HomeAdapter()
        recyclerView?.adapter = recyclerViewAdapter

        presenter.initRecyclerViewData(recyclerViewAdapter)
    }

    override fun isViewActive(): Boolean = checkActive()

    class HomeAdapter: RecyclerView.Adapter<HomeAdapter.ViewHolder>(){
        private lateinit var homeList : ArrayList<Video>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_home,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder){
                homeTitle.text = homeList[position].title
            }

            holder.homePlayButton.setOnClickListener {
                // Play the music
            }
        }

        override fun getItemCount() = homeList.size

        fun setItem(homeList : ArrayList<Video>){
            this.homeList = homeList
        }

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val homeTitle : TextView = view.card_home_title
            val homePlayButton : ImageButton = view.card_home_play_button
        }
    }
}