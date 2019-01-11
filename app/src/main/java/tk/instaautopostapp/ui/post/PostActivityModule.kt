package tk.instaautopostapp.ui.post

import androidx.appcompat.app.AppCompatActivity
import tk.instaautopostapp.ui.base.BaseActivityModule
import tk.instaautopostapp.ui.post.presenter.PostMVPPresenter
import tk.instaautopostapp.ui.post.presenter.PostPresenter
import tk.instaautopostapp.ui.post.view.PostActivity
import tk.instaautopostapp.ui.post.view.PostMVPView
import dagger.Binds
import dagger.Module

@Module(includes = [(BaseActivityModule::class)])
interface PostActivityModule {
    @Binds
    fun provideAppCompatActivity(activity: PostActivity): AppCompatActivity

    @Binds
    fun providePostMVPView(activity: PostActivity): PostMVPView

    @Binds
    fun providePostMVPPresenter(presenter: PostPresenter<PostMVPView>): PostMVPPresenter<PostMVPView>
}