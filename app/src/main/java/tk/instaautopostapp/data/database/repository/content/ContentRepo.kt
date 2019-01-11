package tk.instaautopostapp.data.database.repository.content

import androidx.lifecycle.LiveData
import io.reactivex.Observable

interface ContentRepo {
    fun insertContent(content: Content): Observable<Long>

    fun loadAllContents(): Observable<LiveData<List<Content>>>

    fun loadContentByTaskId(taskId: String): Observable<Content>

    fun deleteAllContents(): Observable<Boolean>
}