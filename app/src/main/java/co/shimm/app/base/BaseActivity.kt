package co.shimm.app.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

abstract class BaseActivity : AppCompatActivity() {
    abstract val layoutRes : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        initView()
        onClickEvent()
    }

    abstract fun initView()
    open fun onClickEvent() {}

    fun checkActive(): Boolean = !isFinishing
}
