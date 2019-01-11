package tk.instaautopostapp.data.database.repository.content

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content")
data class Content(
        @ColumnInfo(name = "user_id")
        var userId: Long,

        @ColumnInfo(name = "task_id")
        var taskId: String,

        @ColumnInfo(name = "type")
        var type: Int,

        @ColumnInfo(name = "date")
        var date: Long,

        @ColumnInfo(name = "file_path")
        var filePath: String,

        @ColumnInfo(name = "file_name")
        var fileName: String,

        @ColumnInfo(name = "status")
        var status: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}