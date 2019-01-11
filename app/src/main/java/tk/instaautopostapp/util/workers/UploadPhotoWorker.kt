package tk.instaautopostapp.util.workers

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import tk.instaautopostapp.App
import tk.instaautopostapp.data.database.repository.content.ContentRepo
import tk.instaautopostapp.data.network.InstagramApi
import tk.instaautopostapp.data.preferences.PreferenceHelper
import tk.instaautopostapp.util.AppConstants
import io.reactivex.Single
import tk.instaautopostapp.di.component.DaggerAppComponent
import java.io.File
import javax.inject.Inject

class UploadPhotoWorker(context: Context, params: WorkerParameters) : RxWorker(context, params) {

    companion object {
        const val EXTRA_FILE_PATH = "extra.filePath"
        const val EXTRA_CAPTION = "extra.caption"
    }

    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    @Inject
    lateinit var instagramApi: InstagramApi
    @Inject
    lateinit var contentRepo: ContentRepo

    override fun createWork(): Single<Result> {
        DaggerAppComponent.builder()
            .application(applicationContext as App)
            .build()
            .inject(this)

        return instagramApi
            .login(preferenceHelper.getUserName(), preferenceHelper.getPassword())
            .flatMap { loginResult ->
                if (loginResult.status == AppConstants.STATUS_OK) {
                    instagramApi.uploadPhoto(
                        File(inputData.getString(EXTRA_FILE_PATH)),
                        inputData.getString(EXTRA_CAPTION)!!
                    )
                } else {
                    Single.error(Throwable())
                }
            }.flatMap { uploadResult ->
                if (uploadResult.status == AppConstants.STATUS_OK) {
                    updateTaskState(id.toString(), AppConstants.UploadStatus.SUCCESSED)
                    Single.just(Result.success())
                }
                else {
                    updateTaskState(id.toString(), AppConstants.UploadStatus.FAILED)
                    Single.just(Result.failure())
                }
            }.doOnError {
                updateTaskState(id.toString(), AppConstants.UploadStatus.FAILED)
                Single.just(Result.failure())
            }
    }

    fun updateTaskState(taskId: String, taskState: AppConstants.UploadStatus) {
        contentRepo.loadContentByTaskId(taskId)
            .flatMap { content ->
                content.status = taskState.status
                contentRepo.insertContent(content)
            }.subscribe({

            }, {

            })
    }
}