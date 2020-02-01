package co.shimm.app.view.activity.login

import android.content.Intent
import android.util.Log
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.const.Const.Login.REQUEST_CODE
import co.shimm.app.data.user.UserInformation
import co.shimm.app.view.activity.agreement.AgreementActivity
import co.shimm.app.view.activity.main.MainActivity
import co.shimm.app.view.activity.payment.PaymentActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_login

    override fun initView() {
        presenter = LoginPresenter(this@LoginActivity, this)
        presenter.start()

        login_login_button.setOnClickListener(this)

        presenter.checkAutoLogin()
    }

    override lateinit var presenter: LoginContract.Presenter

    override fun onClick(v: View) {
        when(v.id){
            R.id.login_login_button -> {
                presenter.signIn()
            }
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE){
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            presenter.checkAccount(task)
        }
    }

    override fun startAgreementActivity(idToken: String?) {
        val intent = Intent(this@LoginActivity, AgreementActivity::class.java)
        UserInformation.idToken = idToken
        startActivity(intent)
    }

    override fun startMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun isViewActive(): Boolean = checkActive()
}