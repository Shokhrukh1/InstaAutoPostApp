package tk.instaautopostapp.ui.base.presenter

import androidx.work.WorkManager
import io.reactivex.disposables.CompositeDisposable
import tk.instaautopostapp.data.database.repository.content.ContentRepo
import tk.instaautopostapp.data.network.InstagramApi
import tk.instaautopostapp.data.preferences.PreferenceHelper
import tk.instaautopostapp.ui.base.view.MVPView
import tk.instaautopostapp.util.RxNetwork
import tk.instaautopostapp.util.SchedulerProvider

abstract class BasePresenter<V: MVPView> constructor(protected var view: V?, protected val instagramApi: InstagramApi, protected val contentRepo: ContentRepo, protected val preferenceHelper: PreferenceHelper, protected val schedulerProvider: SchedulerProvider, protected val compositeDisposable: CompositeDisposable, protected val rxNetwork: RxNetwork) : MVPPresenter<V> {
    override fun logout() {
        preferenceHelper.setUserLoggedIn(false)
        preferenceHelper.setUserId(-1)
        preferenceHelper.setUserName(null)
        preferenceHelper.setPassword(null)

        WorkManager.getInstance().cancelAllWork()

        contentRepo.deleteAllContents()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe({

                }, {

                })

        view?.openLoginActivity()
    }

    override fun onDestroy() {
        view = null
        compositeDisposable.dispose()
    }
}