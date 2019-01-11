package tk.instaautopostapp.ui.taskList.presenter

import tk.instaautopostapp.ui.base.presenter.MVPPresenter
import tk.instaautopostapp.ui.taskList.view.TaskListMVPView

interface TaskListMVPPresenter<V: TaskListMVPView> : MVPPresenter<V> {
    fun getContents()
}