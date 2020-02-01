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
import co.shimm.app.data.room.entity.ShimSchedule
import co.shimm.app.view.activity.payment.PaymentActivity
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.card_schedule.view.*

class ScheduleActivity : BaseActivity(), ScheduleContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_schedule

    private val scheduleRecyclerView by lazy{
        findViewById<RecyclerView>(R.id.schedule_recycler_view)
    }

    override lateinit var presenter: ScheduleContract.Presenter

    override fun initView() {
        presenter = SchedulePresenter(this@ScheduleActivity, this)
        presenter.start()

        val scheduleRecyclerViewAdapter = ScheduleAdapter()

        scheduleRecyclerView?.adapter = scheduleRecyclerViewAdapter

        presenter.initRecyclerViewData(scheduleRecyclerViewAdapter)
    }

    override fun onClick(v: View) {

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
                intent.putExtra("schedule_id", schedule.id)
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
