package tk.instaautopostapp.data.database.repository.content

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import javax.inject.Inject

class ContentRepository @Inject constructor(private val contentDao: ContentDao) : ContentRepo {
    override fun insertContent(content: Content): Observable<Long> {
        return Observable.fromCallable {
            contentDao.insert(content)
        }
    }

    override fun loadAllContents(): Observable<LiveData<List<Content>>> {
        return Observable.fromCallable {
            contentDao.loadAll()
        }
    }

    override fun loadContentByTaskId(taskId: String): Observable<Content> {
        return Observable.fromCallable {
            contentDao.loadContentByTaskId(taskId)
        }
    }

    override fun deleteAllContents(): Observable<Boolean> {
        return Observable.fromCallable {
            contentDao.deleteAll()
            true
        }
    }
}