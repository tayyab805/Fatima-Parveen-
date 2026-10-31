package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.AssignmentItem
import com.example.data.model.BookChapter
import com.example.data.model.LectureItem
import com.example.data.model.StudyNote

@Database(
    entities = [
        BookChapter::class,
        StudyNote::class,
        LectureItem::class,
        AssignmentItem::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chapterDao(): ChapterDao
    abstract fun noteDao(): NoteDao
    abstract fun lectureDao(): LectureDao
    abstract fun assignmentDao(): AssignmentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "noor_study_database.db"
                ).fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
