package tk.instaautopostapp.data.database.repository.content

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(content: Content): Long

    @Query("SELECT * FROM content ORDER BY id DESC")
    fun loadAll(): LiveData<List<Content>>

    @Query("SELECT * FROM content WHERE task_id = :taskId")
    fun loadContentByTaskId(taskId: String): Content

    @Query("DELETE FROM content")
    fun deleteAll()
}