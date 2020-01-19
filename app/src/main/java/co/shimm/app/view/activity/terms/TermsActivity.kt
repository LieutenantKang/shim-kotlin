package co.shimm.app.view.activity.terms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_terms.*

class TermsActivity : BaseActivity(), TermsContract.View {
    override val layoutRes: Int
        get() = R.layout.activity_terms

    override fun initView() {
        presenter = TermsPresenter(this@TermsActivity, this)
        presenter.start()

        val settings = terms_web_view.settings
        settings.javaScriptEnabled = true
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setAppCachePath(cacheDir.path)

        terms_web_view.loadUrl(intent.getStringExtra("link"))
    }

    override lateinit var presenter: TermsContract.Presenter

    override fun isViewActive(): Boolean = checkActive()
}
