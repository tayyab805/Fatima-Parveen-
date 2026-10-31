package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.InitialSyllabusData
import com.example.data.model.AssignmentItem
import com.example.data.model.BookChapter
import com.example.data.model.LectureItem
import com.example.data.model.StudyNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StudyRepository(private val database: AppDatabase) {

    private val chapterDao = database.chapterDao()
    private val noteDao = database.noteDao()
    private val lectureDao = database.lectureDao()
    private val assignmentDao = database.assignmentDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            bootstrapIfEmpty()
        }
    }

    private suspend fun bootstrapIfEmpty() {
        if (chapterDao.getChapterCount() == 0) {
            chapterDao.insertChapters(InitialSyllabusData.getInitialChapters())
        }
        if (noteDao.getNoteCount() == 0) {
            noteDao.insertNotes(InitialSyllabusData.getInitialNotes())
        }
        if (lectureDao.getLectureCount() == 0) {
            lectureDao.insertLectures(InitialSyllabusData.getInitialLectures())
        }
        if (assignmentDao.getAssignmentCount() == 0) {
            assignmentDao.insertAssignments(InitialSyllabusData.getInitialAssignments())
        }
    }

    // Chapters
    val chapters: Flow<List<BookChapter>> = chapterDao.getAllChapters()

    suspend fun getChapter(id: Long): BookChapter? = chapterDao.getChapterById(id)

    suspend fun updateChapter(chapter: BookChapter) = chapterDao.updateChapter(chapter)

    suspend fun addChapter(chapter: BookChapter): Long = chapterDao.insertChapter(chapter)

    // Notes
    val notes: Flow<List<StudyNote>> = noteDao.getAllNotes()

    fun getNotesForChapter(chapterId: Long): Flow<List<StudyNote>> = noteDao.getNotesForChapter(chapterId)

    suspend fun addNote(note: StudyNote): Long = noteDao.insertNote(note)

    suspend fun updateNote(note: StudyNote) = noteDao.updateNote(note)

    suspend fun deleteNote(note: StudyNote) = noteDao.deleteNote(note)

    // Lectures
    val lectures: Flow<List<LectureItem>> = lectureDao.getAllLectures()

    suspend fun updateLecture(lecture: LectureItem) = lectureDao.updateLecture(lecture)

    suspend fun addLecture(lecture: LectureItem): Long = lectureDao.insertLecture(lecture)

    // Assignments
    val assignments: Flow<List<AssignmentItem>> = assignmentDao.getAllAssignments()

    suspend fun getAssignment(id: Long): AssignmentItem? = assignmentDao.getAssignmentById(id)

    suspend fun addAssignment(assignment: AssignmentItem): Long = assignmentDao.insertAssignment(assignment)

    suspend fun updateAssignment(assignment: AssignmentItem) = assignmentDao.updateAssignment(assignment)

    suspend fun deleteAssignment(assignment: AssignmentItem) = assignmentDao.deleteAssignment(assignment)
}
