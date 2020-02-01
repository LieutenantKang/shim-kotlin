package co.shimm.app.view.activity.counselor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.player.ShimPlayerData
import co.shimm.app.view.activity.payment.PaymentActivity
import co.shimm.app.view.activity.schedule.ScheduleActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_counselor.*

class CounselorActivity : BaseActivity(), CounselorContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_counselor

    override fun initView() {
        presenter = CounselorPresenter(this@CounselorActivity)
        presenter.start()

        Glide.with(this).load(ShimPlayerData.shimPlayerCounselor?.picture)
            .error(R.drawable.card_image_sample).into(counselor_thumbnail)

        counselor_name.text = ShimPlayerData.shimPlayerCounselor?.name
        counselor_career.text = ShimPlayerData.shimPlayerCounselor?.career
        counselor_description.text = ShimPlayerData.shimPlayerCounselor?.about

        counselor_apply_button.setOnClickListener(this)
    }

    override lateinit var presenter: CounselorContract.Presenter

    override fun isViewActive(): Boolean = checkActive()

    override fun onClick(v: View) {
        when(v.id){
            R.id.counselor_apply_button -> {
                val intent = Intent(this@CounselorActivity, ScheduleActivity::class.java)
                startActivity(intent)
            }
        }
    }
}