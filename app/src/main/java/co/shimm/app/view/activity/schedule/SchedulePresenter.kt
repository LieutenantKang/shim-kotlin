package co.shimm.app.view.activity.schedule

import android.content.Context
import co.shimm.app.data.model.ScheduleModel

class SchedulePresenter(private val view: ScheduleContract.View, context: Context) : ScheduleContract.Presenter {
    private val scheduleModel: ScheduleModel = ScheduleModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun initRecyclerViewData(scheduleAdapter: ScheduleActivity.ScheduleAdapter) {
        scheduleModel.initRecyclerViewData(scheduleAdapter)
    }
}