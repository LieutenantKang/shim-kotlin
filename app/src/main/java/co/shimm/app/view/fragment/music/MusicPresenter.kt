package co.shimm.app.view.fragment.music

class MusicPresenter (private val view: MusicContract.View) : MusicContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}