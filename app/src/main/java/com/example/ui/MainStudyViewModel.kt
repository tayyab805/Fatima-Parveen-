package com.example.ui

import android.app.Application
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.AssignmentItem
import com.example.data.model.BookChapter
import com.example.data.model.LectureItem
import com.example.data.model.MemoryCard
import com.example.data.model.PuzzleRepository
import com.example.data.model.ScrambleWord
import com.example.data.model.StudyNote
import com.example.data.model.TriviaQuestion
import com.example.data.remote.GeminiAiService
import com.example.data.repository.StudyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

sealed interface AiSolveState {
    data object Idle : AiSolveState
    data object Solving : AiSolveState
    data class Success(val answer: String) : AiSolveState
    data class Error(val message: String) : AiSolveState
}

class MainStudyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StudyRepository(AppDatabase.getInstance(application))
    private val aiService = GeminiAiService()

    // Chapters
    val chapters: StateFlow<List<BookChapter>> = repository.chapters.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _selectedChapter = MutableStateFlow<BookChapter?>(null)
    val selectedChapter: StateFlow<BookChapter?> = _selectedChapter.asStateFlow()

    // Notes
    val notes: StateFlow<List<StudyNote>> = repository.notes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Lectures
    val lectures: StateFlow<List<LectureItem>> = repository.lectures.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Assignments
    val assignments: StateFlow<List<AssignmentItem>> = repository.assignments.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _selectedAssignment = MutableStateFlow<AssignmentItem?>(null)
    val selectedAssignment: StateFlow<AssignmentItem?> = _selectedAssignment.asStateFlow()

    private val _aiSolveState = MutableStateFlow<AiSolveState>(AiSolveState.Idle)
    val aiSolveState: StateFlow<AiSolveState> = _aiSolveState.asStateFlow()

    // TTS Audio Player
    private var textToSpeech: TextToSpeech? = null
    private val _isTtsPlaying = MutableStateFlow(false)
    val isTtsPlaying: StateFlow<Boolean> = _isTtsPlaying.asStateFlow()

    init {
        initTts(application)
    }

    private fun initTts(app: Application) {
        textToSpeech = TextToSpeech(app) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US
            }
        }
    }

    fun speakText(text: String) {
        if (_isTtsPlaying.value) {
            stopTts()
            return
        }
        textToSpeech?.speak(text.take(3000), TextToSpeech.QUEUE_FLUSH, null, "UTTERANCE_ID")
        _isTtsPlaying.value = true
    }

    fun stopTts() {
        textToSpeech?.stop()
        _isTtsPlaying.value = false
    }

    override fun onCleared() {
        super.onCleared()
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }

    // Chapter actions
    fun selectChapter(chapter: BookChapter?) {
        _selectedChapter.value = chapter
        stopTts()
    }

    fun toggleBookmark(chapter: BookChapter) {
        viewModelScope.launch {
            val updated = chapter.copy(isBookmarked = !chapter.isBookmarked)
            repository.updateChapter(updated)
            if (_selectedChapter.value?.id == chapter.id) {
                _selectedChapter.value = updated
            }
        }
    }

    fun updateChapterProgress(chapter: BookChapter, progress: Int) {
        viewModelScope.launch {
            val updated = chapter.copy(progressPercent = progress.coerceIn(0, 100))
            repository.updateChapter(updated)
            if (_selectedChapter.value?.id == chapter.id) {
                _selectedChapter.value = updated
            }
        }
    }

    // Note actions
    fun addNote(title: String, content: String, tag: String, chapterId: Long? = null) {
        viewModelScope.launch {
            val note = StudyNote(
                title = title.ifBlank { "Untitled Note" },
                content = content,
                tag = tag.ifBlank { "General" },
                chapterId = chapterId
            )
            repository.addNote(note)
        }
    }

    fun togglePinNote(note: StudyNote) {
        viewModelScope.launch {
            repository.updateNote(note.copy(isPinned = !note.isPinned))
        }
    }

    fun deleteNote(note: StudyNote) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    // Lecture actions
    fun toggleLectureCompleted(lecture: LectureItem) {
        viewModelScope.launch {
            repository.updateLecture(lecture.copy(isCompleted = !lecture.isCompleted))
        }
    }

    fun updateLectureNotes(lecture: LectureItem, notes: String) {
        viewModelScope.launch {
            repository.updateLecture(lecture.copy(studentNotes = notes))
        }
    }

    fun addCustomLecture(
        title: String,
        urduTitle: String,
        instructor: String,
        duration: Int,
        summary: String,
        takeaways: String
    ) {
        viewModelScope.launch {
            val count = (lectures.value.maxOfOrNull { it.lectureNumber } ?: 0) + 1
            val item = LectureItem(
                chapterNumber = 1,
                lectureNumber = count,
                title = title,
                urduTitle = urduTitle.ifBlank { "نواں لیکچر" },
                instructor = instructor.ifBlank { "Self Study" },
                durationMinutes = duration,
                summary = summary,
                keyTakeaways = takeaways
            )
            repository.addLecture(item)
        }
    }

    // Assignment actions
    fun selectAssignment(assignment: AssignmentItem?) {
        _selectedAssignment.value = assignment
        _aiSolveState.value = if (assignment?.aiAnswer?.isNotBlank() == true) {
            AiSolveState.Success(assignment.aiAnswer)
        } else {
            AiSolveState.Idle
        }
    }

    fun addAssignment(title: String, subject: String, prompt: String, dueDate: String) {
        viewModelScope.launch {
            val item = AssignmentItem(
                title = title,
                subject = subject.ifBlank { "General Science" },
                promptQuestion = prompt,
                dueDate = dueDate
            )
            val newId = repository.addAssignment(item)
            val inserted = repository.getAssignment(newId)
            _selectedAssignment.value = inserted
        }
    }

    fun updateStudentDraft(assignment: AssignmentItem, draft: String) {
        viewModelScope.launch {
            val updated = assignment.copy(studentDraft = draft)
            repository.updateAssignment(updated)
            if (_selectedAssignment.value?.id == assignment.id) {
                _selectedAssignment.value = updated
            }
        }
    }

    fun solveAssignmentWithAi(assignment: AssignmentItem) {
        viewModelScope.launch {
            _aiSolveState.value = AiSolveState.Solving
            val result = aiService.solveAssignment(
                assignmentTitle = assignment.title,
                subject = assignment.subject,
                questionPrompt = assignment.promptQuestion,
                studentDraft = assignment.studentDraft
            )

            result.onSuccess { answer ->
                _aiSolveState.value = AiSolveState.Success(answer)
                val updated = assignment.copy(
                    aiAnswer = answer,
                    status = "Completed"
                )
                repository.updateAssignment(updated)
                if (_selectedAssignment.value?.id == assignment.id) {
                    _selectedAssignment.value = updated
                }
            }.onFailure { error ->
                _aiSolveState.value = AiSolveState.Error(error.localizedMessage ?: "Failed to generate answer")
            }
        }
    }

    fun insertAiAnswerToDraft(assignment: AssignmentItem, aiAnswer: String) {
        viewModelScope.launch {
            val combined = if (assignment.studentDraft.isBlank()) {
                aiAnswer
            } else {
                "${assignment.studentDraft}\n\n--- AI Academic Synthesis ---\n$aiAnswer"
            }
            updateStudentDraft(assignment, combined)
        }
    }

    // -------------------------------------------------------------
    // PUZZLES & GAMES LOGIC
    // -------------------------------------------------------------
    // Game 1: Concept Match Cards
    private val _memoryCards = MutableStateFlow<List<MemoryCard>>(PuzzleRepository.getInitialCards())
    val memoryCards: StateFlow<List<MemoryCard>> = _memoryCards.asStateFlow()

    private val _matchedCount = MutableStateFlow(0)
    val matchedCount: StateFlow<Int> = _matchedCount.asStateFlow()

    private val _moveCount = MutableStateFlow(0)
    val moveCount: StateFlow<Int> = _moveCount.asStateFlow()

    private var firstFlippedIndex: Int? = null
    private var isBusyEvaluating = false

    fun onCardClicked(cardId: Int) {
        if (isBusyEvaluating) return
        val current = _memoryCards.value
        val clickedIndex = current.indexOfFirst { it.id == cardId }
        if (clickedIndex == -1) return
        val card = current[clickedIndex]
        if (card.isFaceUp || card.isMatched) return

        // Flip card face up
        _memoryCards.value = current.mapIndexed { i, c ->
            if (i == clickedIndex) c.copy(isFaceUp = true) else c
        }

        if (firstFlippedIndex == null) {
            firstFlippedIndex = clickedIndex
        } else {
            val firstIdx = firstFlippedIndex!!
            firstFlippedIndex = null
            _moveCount.value += 1

            val firstCard = current[firstIdx]
            val secondCard = card

            if (firstCard.conceptId == secondCard.conceptId) {
                // Match found!
                _matchedCount.value += 1
                _memoryCards.value = _memoryCards.value.map { c ->
                    if (c.id == firstCard.id || c.id == secondCard.id) {
                        c.copy(isMatched = true, isFaceUp = true)
                    } else c
                }
            } else {
                // Not a match, flip back down after delay
                isBusyEvaluating = true
                viewModelScope.launch {
                    delay(1100)
                    _memoryCards.value = _memoryCards.value.map { c ->
                        if (c.id == firstCard.id || c.id == secondCard.id) {
                            c.copy(isFaceUp = false)
                        } else c
                    }
                    isBusyEvaluating = false
                }
            }
        }
    }

    fun resetMemoryGame() {
        firstFlippedIndex = null
        isBusyEvaluating = false
        _matchedCount.value = 0
        _moveCount.value = 0
        _memoryCards.value = PuzzleRepository.getInitialCards()
    }

    // Game 2: Word Scramble
    private val scrambleList = PuzzleRepository.getScrambleWords()
    private val _scrambleIndex = MutableStateFlow(0)
    val scrambleIndex: StateFlow<Int> = _scrambleIndex.asStateFlow()

    val currentScrambleWord: StateFlow<ScrambleWord> = MutableStateFlow(scrambleList[0]).also { flow ->
        viewModelScope.launch {
            _scrambleIndex.collect { idx ->
                flow.value = scrambleList[idx % scrambleList.size]
            }
        }
    }

    private val _scrambleScore = MutableStateFlow(0)
    val scrambleScore: StateFlow<Int> = _scrambleScore.asStateFlow()

    private val _scrambleFeedback = MutableStateFlow<String?>(null)
    val scrambleFeedback: StateFlow<String?> = _scrambleFeedback.asStateFlow()

    fun submitScrambleGuess(guess: String) {
        val word = currentScrambleWord.value.originalWord
        if (guess.trim().equals(word, ignoreCase = true)) {
            _scrambleScore.value += 10
            _scrambleFeedback.value = "Correct! +10 Points (شاباش!)"
            viewModelScope.launch {
                delay(1200)
                _scrambleIndex.value = (_scrambleIndex.value + 1) % scrambleList.size
                _scrambleFeedback.value = null
            }
        } else {
            _scrambleFeedback.value = "Try again! Look closely at the scrambled letters."
        }
    }

    fun skipScrambleWord() {
        _scrambleIndex.value = (_scrambleIndex.value + 1) % scrambleList.size
        _scrambleFeedback.value = null
    }

    // Game 3: Trivia Quest
    private val triviaQuestions = PuzzleRepository.getTriviaQuestions()
    private val _triviaIndex = MutableStateFlow(0)
    val triviaIndex: StateFlow<Int> = _triviaIndex.asStateFlow()

    val currentTrivia: StateFlow<TriviaQuestion> = MutableStateFlow(triviaQuestions[0]).also { flow ->
        viewModelScope.launch {
            _triviaIndex.collect { idx ->
                flow.value = triviaQuestions[idx % triviaQuestions.size]
            }
        }
    }

    private val _selectedTriviaOption = MutableStateFlow<Int?>(null)
    val selectedTriviaOption: StateFlow<Int?> = _selectedTriviaOption.asStateFlow()

    private val _triviaScore = MutableStateFlow(0)
    val triviaScore: StateFlow<Int> = _triviaScore.asStateFlow()

    private val _triviaStreak = MutableStateFlow(0)
    val triviaStreak: StateFlow<Int> = _triviaStreak.asStateFlow()

    fun selectTriviaOption(optionIndex: Int) {
        if (_selectedTriviaOption.value != null) return // Already submitted for this question
        _selectedTriviaOption.value = optionIndex
        val q = currentTrivia.value
        if (optionIndex == q.correctIndex) {
            _triviaScore.value += 20
            _triviaStreak.value += 1
        } else {
            _triviaStreak.value = 0
        }
    }

    fun nextTriviaQuestion() {
        _selectedTriviaOption.value = null
        _triviaIndex.value = (_triviaIndex.value + 1) % triviaQuestions.size
    }

    fun resetTrivia() {
        _selectedTriviaOption.value = null
        _triviaIndex.value = 0
        _triviaScore.value = 0
        _triviaStreak.value = 0
    }
}
