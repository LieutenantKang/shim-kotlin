package co.shimm.app.view.activity.login

import android.content.Intent
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.view.activity.main.MainActivity
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
    }

    override lateinit var presenter: LoginContract.Presenter

    override fun onClick(v: View) {
        when(v.id){
            R.id.login_login_button -> {
//                presenter!!.signIn()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 3333){
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            presenter.checkAccount(task)
        }
    }

    override fun isViewActive(): Boolean = checkActive()
}