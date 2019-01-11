package tk.instaautopostapp.util

import android.content.Context
import android.net.ConnectivityManager
import com.quiztestapp.util.exception.NetworkNotAvailableException
import io.reactivex.Observable
import io.reactivex.Single

class RxNetwork(val context: Context) {
    fun checkNetworkAvailableWithException(): Observable<Boolean> {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.let {
            return Observable.just(true)
        }

        return Observable.error(NetworkNotAvailableException())
    }

    fun checkNetworkAvailable(): Observable<Boolean> {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.let {
            return Observable.just(true)
        }

        return Observable.just(false)
    }
}