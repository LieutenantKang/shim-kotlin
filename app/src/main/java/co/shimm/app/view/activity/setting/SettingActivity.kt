package co.shimm.app.view.activity.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.view.activity.login.LoginActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity(), SettingContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_setting

    override lateinit var presenter: SettingContract.Presenter

    override fun initView() {
        presenter = SettingPresenter(this@SettingActivity, this)
        presenter.start()

        setting_logout_button.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.setting_logout_button -> {
                presenter.logout()

                val intent = Intent(this@SettingActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }

    override fun isViewActive(): Boolean = checkActive()
}
