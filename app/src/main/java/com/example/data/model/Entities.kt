package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_chapters")
data class BookChapter(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterNumber: Int,
    val title: String,
    val urduTitle: String,
    val category: String,
    val estimatedMinutes: Int = 12,
    val summary: String,
    val fullContent: String,
    val keyFormulasAndTerms: String, // Comma or newline separated
    val isBookmarked: Boolean = false,
    val progressPercent: Int = 0
)

@Entity(tableName = "study_notes")
data class StudyNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterId: Long? = null,
    val title: String,
    val content: String,
    val tag: String = "General",
    val isPinned: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "lectures")
data class LectureItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterNumber: Int,
    val lectureNumber: Int,
    val title: String,
    val urduTitle: String,
    val instructor: String,
    val durationMinutes: Int,
    val summary: String,
    val keyTakeaways: String,
    val isCompleted: Boolean = false,
    val studentNotes: String = ""
)

@Entity(tableName = "assignments")
data class AssignmentItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val subject: String,
    val promptQuestion: String,
    val studentDraft: String = "",
    val aiAnswer: String = "",
    val status: String = "Pending", // "Pending", "Solving", "Completed"
    val dueDate: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)
