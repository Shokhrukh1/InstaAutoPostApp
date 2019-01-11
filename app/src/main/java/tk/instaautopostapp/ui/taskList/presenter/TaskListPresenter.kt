package tk.instaautopostapp.ui.taskList.presenter

import tk.instaautopostapp.data.database.repository.content.ContentRepo
import tk.instaautopostapp.data.network.InstagramApi
import tk.instaautopostapp.data.preferences.PreferenceHelper
import tk.instaautopostapp.ui.base.presenter.BasePresenter
import tk.instaautopostapp.ui.taskList.view.TaskListMVPView
import tk.instaautopostapp.util.RxNetwork
import tk.instaautopostapp.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TaskListPresenter<V : TaskListMVPView> @Inject constructor(view: V, instagramApi: InstagramApi, contentRepo: ContentRepo, preferenceHelper: PreferenceHelper, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable, rxNetwork: RxNetwork) : BasePresenter<V>(view, instagramApi, contentRepo, preferenceHelper, schedulerProvider, compositeDisposable, rxNetwork), TaskListMVPPresenter<V> {
    override fun getContents() {
        compositeDisposable.add(contentRepo.loadAllContents()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe({ contents ->
                    view?.showContents(contents)
                }, {

                }))
    }
}