package tk.instaautopostapp.ui.login

import androidx.appcompat.app.AppCompatActivity
import tk.instaautopostapp.ui.base.BaseActivityModule
import tk.instaautopostapp.ui.login.presenter.LoginMVPPresenter
import tk.instaautopostapp.ui.login.presenter.LoginPresenter
import tk.instaautopostapp.ui.login.view.LoginActivity
import tk.instaautopostapp.ui.login.view.LoginMVPView
import dagger.Module
import dagger.Provides

@Module(includes = [(BaseActivityModule::class)])
class LoginActivityModule {
    @Provides
    fun provideAppCompatActivity(activity: LoginActivity): AppCompatActivity = activity

    @Provides
    fun provideLoginMVPView(activity: LoginActivity): LoginMVPView = activity

    @Provides
    fun provideLoginMVPPresenter(presenter: LoginPresenter<LoginMVPView>): LoginMVPPresenter<LoginMVPView> = presenter
}