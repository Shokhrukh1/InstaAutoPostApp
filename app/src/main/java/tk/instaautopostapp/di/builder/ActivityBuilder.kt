package tk.instaautopostapp.di.builder

import tk.instaautopostapp.ui.login.LoginActivityModule
import tk.instaautopostapp.ui.login.view.LoginActivity
import tk.instaautopostapp.ui.post.PostActivityModule
import tk.instaautopostapp.ui.post.view.PostActivity
import tk.instaautopostapp.ui.taskList.TaskListActivityModule
import tk.instaautopostapp.ui.taskList.view.TaskListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [(TaskListActivityModule::class)])
    abstract fun bindTaskListActivity(): TaskListActivity

    @ContributesAndroidInjector(modules = [(PostActivityModule::class)])
    abstract fun bindPostActivity(): PostActivity
}