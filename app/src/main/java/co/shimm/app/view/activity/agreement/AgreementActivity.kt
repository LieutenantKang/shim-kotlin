package co.shimm.app.view.activity.agreement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.shimm.app.R
import co.shimm.app.base.BaseActivity
import co.shimm.app.view.activity.terms.TermsActivity
import kotlinx.android.synthetic.main.activity_agreement.*

class AgreementActivity : BaseActivity(), AgreementContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_agreement

    override fun initView() {
        presenter = AgreementPresenter(this@AgreementActivity, this)
        presenter.start()

        agreement_checkbox_age.setOnClickListener(this)
        agreement_checkbox_privacy.setOnClickListener(this)
        agreement_checkbox_service.setOnClickListener(this)
        agreement_checkbox_all.setOnClickListener(this)
        agreement_link_privacy.setOnClickListener(this)
        agreement_link_service.setOnClickListener(this)
    }

    override lateinit var presenter: AgreementContract.Presenter

    override fun isViewActive(): Boolean = checkActive()

    private fun startTermsActivity(link: String){
        val intent = Intent(this, TermsActivity::class.java)
        intent.putExtra("link", link)
        this.startActivity(intent)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.agreement_link_privacy -> {
                startTermsActivity("https://astro36.github.io/shim-documents/privacy-policy")
            }
            R.id.agreement_link_service -> {
                startTermsActivity("https://astro36.github.io/shim-documents/terms")
            }
            R.id.agreement_checkbox_all -> {
                if(agreement_checkbox_all.isChecked){
                    agreement_checkbox_service.isChecked = true
                    agreement_checkbox_privacy.isChecked = true
                    agreement_checkbox_age.isChecked = true
                }else{
                    agreement_checkbox_service.isChecked = false
                    agreement_checkbox_privacy.isChecked = false
                    agreement_checkbox_age.isChecked = false
                }
            }
            R.id.agreement_checkbox_age -> {
                if(!agreement_checkbox_age.isChecked){
                    agreement_checkbox_all.isChecked = false
                }
            }
            R.id.agreement_checkbox_privacy -> {
                if(!agreement_checkbox_privacy.isChecked){
                    agreement_checkbox_all.isChecked = false
                }
            }
            R.id.agreement_checkbox_service -> {
                if(!agreement_checkbox_service.isChecked){
                    agreement_checkbox_all.isChecked = false
                }
            }
        }
    }
}
