package tk.instaautopostapp.ui.login.presenter

import tk.instaautopostapp.data.database.repository.content.ContentRepo
import tk.instaautopostapp.data.network.InstagramApi
import tk.instaautopostapp.data.preferences.PreferenceHelper
import tk.instaautopostapp.ui.base.presenter.BasePresenter
import tk.instaautopostapp.ui.login.view.LoginMVPView
import tk.instaautopostapp.util.AppConstants
import tk.instaautopostapp.util.RxNetwork
import tk.instaautopostapp.util.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter<V : LoginMVPView> @Inject constructor(
    view: V,
    instagramApi: InstagramApi,
    contentRepo: ContentRepo,
    preferenceHelper: PreferenceHelper,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    rxNetwork: RxNetwork
) : BasePresenter<V>(view, instagramApi, contentRepo, preferenceHelper, schedulerProvider, compositeDisposable, rxNetwork),
    LoginMVPPresenter<V> {
    override fun checkUserLoggedIn() {
        if (preferenceHelper.isUserLoggedIn()) {
            login(preferenceHelper.getUserName(), preferenceHelper.getPassword())
        }
    }

    override fun login(userName: String, password: String) {
        view?.showProgress()

        if (!instagramApi.isLoggedIn().blockingSingle()) {
            instagramApi.login(userName, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ loginResult ->
                        if (loginResult.status == AppConstants.STATUS_OK) {
                            preferenceHelper.setUserLoggedIn(true)
                            preferenceHelper.setUserName(userName)
                            preferenceHelper.setPassword(password)
                            preferenceHelper.setUserId(loginResult.logged_in_user.pk)

                            view?.openTaskListActivity()
                        } else {
                            view?.showToast(loginResult.message)
                        }
                    }, {
                        view?.hideProgress()
                        it.message?.let { message ->
                            view?.showToast(message)
                        }
                    })
        } else {
            view?.hideProgress()
            view?.openTaskListActivity()
        }
    }
}