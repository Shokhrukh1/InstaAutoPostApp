package tk.instaautopostapp.ui.post.presenter

import android.net.Uri
import androidx.work.*
import tk.instaautopostapp.data.database.repository.content.Content
import tk.instaautopostapp.data.database.repository.content.ContentRepo
import tk.instaautopostapp.data.network.InstagramApi
import tk.instaautopostapp.data.preferences.PreferenceHelper
import tk.instaautopostapp.ui.base.presenter.BasePresenter
import tk.instaautopostapp.ui.post.view.PostMVPView
import tk.instaautopostapp.util.AppConstants
import tk.instaautopostapp.util.workers.UploadPhotoWorker
import tk.instaautopostapp.util.workers.UploadVideoWorker
import tk.instaautopostapp.util.RxNetwork
import tk.instaautopostapp.util.SchedulerProvider
import id.zelory.compressor.Compressor
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PostPresenter<V : PostMVPView> @Inject constructor(
        val compressor: Compressor,
        view: V,
        instagramApi: InstagramApi,
        contentRepo: ContentRepo,
        preferenceHelper: PreferenceHelper,
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        rxNetwork: RxNetwork
) : BasePresenter<V>(view, instagramApi, contentRepo, preferenceHelper, schedulerProvider, compositeDisposable, rxNetwork),
        PostMVPPresenter<V> {
    override fun uploadPhoto(path: String, cutPath: String, caption: String, milliseconds: Long) {
        var time = 0L
        val currentTime = Calendar.getInstance().timeInMillis
        val file = compressor.compressToFile(File(cutPath))

        view?.showProgress()

        if (currentTime < milliseconds) {
            time = Math.abs(Calendar.getInstance().timeInMillis - milliseconds)
        }

        val work = buildWorker(true, file.absolutePath, caption, time)

        WorkManager.getInstance()
                .beginWith(work)
                .enqueue()

        writeContentToDatabase(work.id.toString(),
                AppConstants.ContentType.IMAGE,
                milliseconds,
                path,
                cutPath)
    }

    override fun uploadVideo(path: String, caption: String, milliseconds: Long) {
        var time = 0L
        val currentTime = Calendar.getInstance().timeInMillis

        view?.showProgress()

        if (currentTime < milliseconds) {
            time = Math.abs(Calendar.getInstance().timeInMillis - milliseconds)
        }

        val work = buildWorker(false, path, caption, time)

        WorkManager.getInstance()
                .beginWith(work)
                .enqueue()

        writeContentToDatabase(work.id.toString(),
                AppConstants.ContentType.VIDEO,
                milliseconds,
                path,
                path)
    }

    private fun buildWorker(isPhotoBuilder: Boolean, path: String, caption: String, milliseconds: Long): OneTimeWorkRequest {
        val builder = if (isPhotoBuilder)
            OneTimeWorkRequest.Builder(UploadPhotoWorker::class.java)
        else
            OneTimeWorkRequest.Builder(UploadVideoWorker::class.java)

        return builder.setInputData(
                Data.Builder()
                        .putString(UploadPhotoWorker.EXTRA_FILE_PATH, path)
                        .putString(UploadPhotoWorker.EXTRA_CAPTION, caption)
                        .build())
                .setInitialDelay(milliseconds, TimeUnit.MILLISECONDS)
                .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build()
    }

    private fun writeContentToDatabase(taskId: String, contentType: AppConstants.ContentType, milliseconds: Long, path: String, cutPath: String) {
        contentRepo.insertContent(
                Content(preferenceHelper.getUserId(),
                        taskId,
                        contentType.type,
                        milliseconds,
                        cutPath,
                        Uri.parse(path).lastPathSegment,
                        AppConstants.UploadStatus.NONE.status))
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .doFinally {
                    view?.hideProgress()
                }.subscribe({
                    view?.closeActivity()
                }, {

                })
    }
}