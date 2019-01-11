package tk.instaautopostapp.data.network

import dev.niekirk.com.instagram4android.Instagram4Android
import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest
import dev.niekirk.com.instagram4android.requests.InstagramUploadVideoRequest
import dev.niekirk.com.instagram4android.requests.payload.InstagramConfigurePhotoResult
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult
import dev.niekirk.com.instagram4android.requests.payload.StatusResult
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File

class InstagramApi(val instagram: Instagram4Android) {
    fun login(userName: String, password: String): Single<InstagramLoginResult> {
        return Single.fromCallable {
            instagram.username = userName
            instagram.password = password
            instagram.setup()

            instagram.login()
        }
    }

    fun uploadPhoto(file: File, caption: String): Single<InstagramConfigurePhotoResult> {
        return Single.fromCallable {
            instagram.sendRequest(InstagramUploadPhotoRequest(file, caption))
        }
    }

    fun uploadVideo(file: File, caption: String): Single<StatusResult> {
        return Single.fromCallable {
            instagram.sendRequest(InstagramUploadVideoRequest(file, caption))
        }
    }

    fun isLoggedIn(): Observable<Boolean> {
        return Observable.just(instagram.isLoggedIn)
    }
}