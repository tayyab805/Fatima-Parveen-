package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.AssignmentItem
import com.example.data.model.BookChapter
import com.example.data.model.LectureItem
import com.example.data.model.StudyNote
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Query("SELECT * FROM book_chapters ORDER BY chapterNumber ASC")
    fun getAllChapters(): Flow<List<BookChapter>>

    @Query("SELECT * FROM book_chapters WHERE id = :id LIMIT 1")
    suspend fun getChapterById(id: Long): BookChapter?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<BookChapter>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapter: BookChapter): Long

    @Update
    suspend fun updateChapter(chapter: BookChapter)

    @Query("SELECT COUNT(*) FROM book_chapters")
    suspend fun getChapterCount(): Int
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM study_notes ORDER BY isPinned DESC, createdAt DESC")
    fun getAllNotes(): Flow<List<StudyNote>>

    @Query("SELECT * FROM study_notes WHERE chapterId = :chapterId ORDER BY createdAt DESC")
    fun getNotesForChapter(chapterId: Long): Flow<List<StudyNote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: StudyNote): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<StudyNote>)

    @Update
    suspend fun updateNote(note: StudyNote)

    @Delete
    suspend fun deleteNote(note: StudyNote)

    @Query("SELECT COUNT(*) FROM study_notes")
    suspend fun getNoteCount(): Int
}

@Dao
interface LectureDao {
    @Query("SELECT * FROM lectures ORDER BY chapterNumber ASC, lectureNumber ASC")
    fun getAllLectures(): Flow<List<LectureItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLectures(lectures: List<LectureItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLecture(lecture: LectureItem): Long

    @Update
    suspend fun updateLecture(lecture: LectureItem)

    @Delete
    suspend fun deleteLecture(lecture: LectureItem)

    @Query("SELECT COUNT(*) FROM lectures")
    suspend fun getLectureCount(): Int
}

@Dao
interface AssignmentDao {
    @Query("SELECT * FROM assignments ORDER BY createdAt DESC")
    fun getAllAssignments(): Flow<List<AssignmentItem>>

    @Query("SELECT * FROM assignments WHERE id = :id LIMIT 1")
    suspend fun getAssignmentById(id: Long): AssignmentItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignment(assignment: AssignmentItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignments(assignments: List<AssignmentItem>)

    @Update
    suspend fun updateAssignment(assignment: AssignmentItem)

    @Delete
    suspend fun deleteAssignment(assignment: AssignmentItem)

    @Query("SELECT COUNT(*) FROM assignments")
    suspend fun getAssignmentCount(): Int
}
