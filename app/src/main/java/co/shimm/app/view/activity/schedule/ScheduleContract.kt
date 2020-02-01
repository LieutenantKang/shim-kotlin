package co.shimm.app.view.activity.schedule

import co.shimm.app.base.BaseContract

interface ScheduleContract {
    interface View: BaseContract.BaseView<Presenter>{
    }

    interface Presenter : BaseContract.BasePresenter{
        fun initRecyclerViewData(scheduleAdapter: ScheduleActivity.ScheduleAdapter)
    }
}