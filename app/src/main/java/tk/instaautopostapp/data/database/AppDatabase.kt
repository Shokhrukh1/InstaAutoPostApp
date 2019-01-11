package tk.instaautopostapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import tk.instaautopostapp.data.database.repository.content.Content
import tk.instaautopostapp.data.database.repository.content.ContentDao

@Database(entities = [(Content::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}