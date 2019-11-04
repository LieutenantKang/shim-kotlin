package co.shimm.app.view.fragment.home

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}