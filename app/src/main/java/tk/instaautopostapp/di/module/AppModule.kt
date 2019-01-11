package tk.instaautopostapp.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import tk.instaautopostapp.data.database.AppDatabase
import tk.instaautopostapp.data.database.repository.content.ContentRepo
import tk.instaautopostapp.data.database.repository.content.ContentRepository
import tk.instaautopostapp.data.network.InstagramApi
import tk.instaautopostapp.data.preferences.AppPreferenceHelper
import tk.instaautopostapp.data.preferences.PreferenceHelper
import tk.instaautopostapp.di.PreferenceInfo
import tk.instaautopostapp.util.AppConstants
import tk.instaautopostapp.util.RxNetwork
import tk.instaautopostapp.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import dev.niekirk.com.instagram4android.Instagram4Android
import id.zelory.compressor.Compressor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppConstants.APP_DB_NAME)
            .build()

    @Provides
    @Singleton
    fun provideInstagram() : Instagram4Android = Instagram4Android.builder().build()

    @Provides
    @Singleton
    fun provideInstagramApi(instagram: Instagram4Android): InstagramApi = InstagramApi(instagram)

    @Provides
    @PreferenceInfo
    fun providePrefFileName(): String = AppConstants.PREF_NAME

    @Provides
    @Singleton
    fun providePrefHelper(appPreferenceHelper: AppPreferenceHelper): PreferenceHelper = appPreferenceHelper

    @Provides
    @Singleton
    fun provideRxNetwork(context: Context) = RxNetwork(context)

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider() = SchedulerProvider()

    @Provides
    fun provideCompressor(context: Context) = Compressor(context)

    @Provides
    fun provideContentRepoHelper(appDatabase: AppDatabase): ContentRepo = ContentRepository(appDatabase.contentDao())
}