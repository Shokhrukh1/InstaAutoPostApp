package tk.instaautopostapp.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides

@Module
class BaseActivityModule {
    @Provides
    fun provideRxPermission(activity: AppCompatActivity)= RxPermissions(activity)
}