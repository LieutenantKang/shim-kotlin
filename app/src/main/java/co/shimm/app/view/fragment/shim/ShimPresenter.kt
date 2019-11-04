package co.shimm.app.view.fragment.shim

class ShimPresenter  (private val view: ShimContract.View) : ShimContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}