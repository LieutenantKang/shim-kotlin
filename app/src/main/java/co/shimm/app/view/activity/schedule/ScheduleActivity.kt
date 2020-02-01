package co.shimm.app.view.activity.schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.player.ShimPlayerData
import co.shimm.app.data.room.entity.ShimSchedule
import co.shimm.app.view.activity.payment.PaymentActivity
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_counselor.*
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlinx.android.synthetic.main.card_schedule.view.*

class ScheduleActivity : BaseActivity(), ScheduleContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_schedule

    private val scheduleRecyclerView by lazy{
        findViewById<RecyclerView>(R.id.schedule_recycler_view)
    }

    val scheduleRecyclerViewAdapter = ScheduleAdapter()

    override lateinit var presenter: ScheduleContract.Presenter

    override fun initView() {
        presenter = SchedulePresenter(this@ScheduleActivity, this)
        presenter.start()

        Glide.with(this).load(ShimPlayerData.shimPlayerCounselor?.picture)
            .error(R.drawable.card_image_sample).into(schedule_counselor_thumbnail)

        schedule_counselor_name.text = ShimPlayerData.shimPlayerCounselor?.name
//        schedule_price_30.text = ShimPlayerData.shimPlayerCounselor?.online30Cost.toString()

        scheduleRecyclerView?.adapter = scheduleRecyclerViewAdapter

        schedule_checkbox_30.setOnClickListener(this)
        schedule_checkbox_30.isChecked = true

        presenter.initRecyclerViewData(scheduleRecyclerViewAdapter)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.schedule_checkbox_30 -> {
                if(schedule_checkbox_30.isChecked) {
//                    presenter.initRecyclerViewData(scheduleRecyclerViewAdapter)
                }
            }
        }
    }

    override fun isViewActive(): Boolean = checkActive()

    class ScheduleAdapter: RecyclerView.Adapter<ScheduleAdapter.ViewHolder>(){
        private lateinit var scheduleList : ArrayList<ShimSchedule>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_schedule, parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val schedule = scheduleList[position]

            with(holder){
                scheduleTime.text = String.format("%d:%d",schedule.startTime.hours, schedule.startTime.minutes)
            }
            holder.scheduleLayout.setOnClickListener{
                val intent = Intent(holder.itemView.context, PaymentActivity::class.java)
                intent.putExtra("schedule_id", schedule.id.toString())
                holder.itemView.context.startActivity(intent)
            }
        }

        override fun getItemCount() = scheduleList.size

        fun setItem(scheduleList : ArrayList<ShimSchedule>){
            this.scheduleList = scheduleList
        }

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val scheduleTime : TextView = view.card_schedule_time
            val scheduleLayout : MaterialCardView = view.card_schedule_layout
        }
    }
}
