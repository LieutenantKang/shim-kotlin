package co.shimm.app.view.activity.counselor

class CounselorPresenter(private val view: CounselorContract.View) : CounselorContract.Presenter {
    override fun start() {
        view.presenter = this
    }
}