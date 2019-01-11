package tk.instaautopostapp.di.component

import android.app.Application
import tk.instaautopostapp.App
import tk.instaautopostapp.di.builder.ActivityBuilder
import tk.instaautopostapp.di.module.AppModule
import tk.instaautopostapp.util.workers.UploadPhotoWorker
import tk.instaautopostapp.util.workers.UploadVideoWorker
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (AppModule::class), (ActivityBuilder::class)])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

    fun inject(uploadPhotoWorker: UploadPhotoWorker)

    fun inject(uploadVideoWorker: UploadVideoWorker)
}