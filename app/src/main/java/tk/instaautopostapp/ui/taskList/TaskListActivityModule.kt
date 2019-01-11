package tk.instaautopostapp.ui.taskList

import androidx.appcompat.app.AppCompatActivity
import tk.instaautopostapp.ui.base.BaseActivityModule
import tk.instaautopostapp.ui.taskList.presenter.TaskListMVPPresenter
import tk.instaautopostapp.ui.taskList.presenter.TaskListPresenter
import tk.instaautopostapp.ui.taskList.view.TaskListActivity
import tk.instaautopostapp.ui.taskList.view.TaskListMVPView
import dagger.Module
import dagger.Provides

@Module(includes = [(BaseActivityModule::class)])
class TaskListActivityModule {
    @Provides
    fun provideAppCompatActivity(activity: TaskListActivity): AppCompatActivity = activity

    @Provides
    fun provideTaskListMVPView(activity: TaskListActivity): TaskListMVPView = activity

    @Provides
    fun provideTaskListMVPPresenter(presenter: TaskListPresenter<TaskListMVPView>): TaskListMVPPresenter<TaskListMVPView> = presenter
}