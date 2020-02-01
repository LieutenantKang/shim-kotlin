package co.shimm.app.view.activity.payment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.CookieManager
import android.webkit.WebSettings
import co.shimm.app.BuildConfig
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.data.user.UserInformation
import co.shimm.app.data.iamportsdk.InicisWebViewClient
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : BaseActivity(), PaymentContract.View {

    var APP_SCHEME : String = "iamporttest://"

    override val layoutRes: Int
        get() = R.layout.activity_payment

    override fun initView() {
        presenter = PaymentPresenter(this@PaymentActivity, this)
        presenter.start()

        payment_web_view.webViewClient = InicisWebViewClient(this)
        val settings: WebSettings = payment_web_view.settings
        settings.javaScriptEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            var cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.acceptThirdPartyCookies(payment_web_view)
        }

        val intent : Intent = intent
        val intentData : Uri? = intent.data

        if(intentData == null){
            val paymentHeader : HashMap<String?, String?> = hashMapOf("Authorization" to "Bearer "+UserInformation.token)
            payment_web_view.loadUrl(BuildConfig.SERVER_URL+"views/payments/schedules/"
                +intent.getStringExtra("schedule_id")+"?unit=30m", paymentHeader)
        }else{
            val url = intentData.toString()
            if(url.startsWith(APP_SCHEME)){
                val redirectURL = url.substring(APP_SCHEME.length + 3)
                payment_web_view.loadUrl(redirectURL)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val url = intent.toString()
        if(url.startsWith(APP_SCHEME)){
            val redirectURL = url.substring(APP_SCHEME.length + 3)
            payment_web_view.loadUrl(redirectURL)
        }
    }

    override lateinit var presenter: PaymentContract.Presenter

    override fun isViewActive(): Boolean = checkActive()
}
