package com.example.data.model

data class MemoryCard(
    val id: Int,
    val conceptId: Int,
    val text: String,
    val isConceptName: Boolean,
    val isFaceUp: Boolean = false,
    val isMatched: Boolean = false
)

data class ScrambleWord(
    val originalWord: String,
    val scrambledWord: String,
    val hint: String,
    val category: String
)

data class TriviaQuestion(
    val id: Int,
    val question: String,
    val urduQuestion: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val category: String
)

object PuzzleRepository {
    fun getInitialCards(): List<MemoryCard> {
        return listOf(
            MemoryCard(1, 101, "MergeSort", true),
            MemoryCard(2, 101, "Guaranteed O(n log n) Divide & Conquer", false),
            MemoryCard(3, 102, "Entropy (ΔS)", true),
            MemoryCard(4, 102, "Degree of disorder in a closed system", false),
            MemoryCard(5, 103, "Derivative", true),
            MemoryCard(6, 103, "Instantaneous rate of change (slope)", false),
            MemoryCard(7, 104, "Self-Attention", true),
            MemoryCard(8, 104, "Q, K, V weighting in Transformers", false)
        ).shuffled()
    }

    fun getScrambleWords(): List<ScrambleWord> {
        return listOf(
            ScrambleWord("ALGORITHM", "MROTHGLIA", "A step-by-step computational procedure to solve a problem", "Computer Science"),
            ScrambleWord("ENTROPY", "POTERNY", "Thermodynamic measure of universal disorder (ΔS)", "Natural Sciences"),
            ScrambleWord("CALCULUS", "SUCLACLU", "Mathematical study of continuous change and accumulation", "Mathematics"),
            ScrambleWord("ATTENTION", "TETANOITN", "Core mechanism powering modern Transformer AI models", "Artificial Intelligence"),
            ScrambleWord("HYPOTHESIS", "SEYHIHPTOS", "A testable and falsifiable academic proposition", "Research Methodology"),
            ScrambleWord("GRADIENT", "TNAIDERG", "Direction and magnitude of steepest rate of increase", "Machine Learning")
        )
    }

    fun getTriviaQuestions(): List<TriviaQuestion> {
        return listOf(
            TriviaQuestion(
                id = 1,
                question = "What is the worst-case time complexity of MergeSort?",
                urduQuestion = "مرج سارٹ کی بدترین صورتحال کی ٹائم کمپلیکسٹی کیا ہے؟",
                options = listOf("O(n)", "O(n log n)", "O(n²)", "O(log n)"),
                correctIndex = 1,
                explanation = "MergeSort recursively bisects the list and merges in linear time, guaranteeing O(n log n) in all cases.",
                category = "Computer Science"
            ),
            TriviaQuestion(
                id = 2,
                question = "According to the Second Law of Thermodynamics, what happens to the total entropy of an isolated system?",
                urduQuestion = "تھرموڈینامکس کے دوسرے قانون کے مطابق بند نظام کی کل اینٹروپی کا کیا بنتا ہے؟",
                options = listOf("Always decreases", "Remains constant", "Always increases or stays constant", "Approaches zero"),
                correctIndex = 2,
                explanation = "The Second Law dictates that in any spontaneous natural process, ΔS_universe ≥ 0.",
                category = "Natural Sciences"
            ),
            TriviaQuestion(
                id = 3,
                question = "If f(x) = x³ - 3x, what is the value of f'(1)?",
                urduQuestion = "اگر f(x) = x³ - 3x ہو تو f'(1) کی قیمت کیا ہوگی؟",
                options = listOf("0", "3", "-2", "6"),
                correctIndex = 0,
                explanation = "f'(x) = 3x² - 3. Evaluating at x = 1 yields 3(1)² - 3 = 0 (a critical extremity).",
                category = "Mathematics"
            ),
            TriviaQuestion(
                id = 4,
                question = "In the Transformer architecture, what do Q, K, and V represent in the attention formula?",
                urduQuestion = "ٹرانسفارمرز میں اٹینشن کے فارمولے کے اندر Q، K اور V کیا ظاہر کرتے ہیں؟",
                options = listOf("Quick, Known, Value", "Query, Key, Value", "Queue, Kernel, Vector", "Quantity, Knowledge, Velocity"),
                correctIndex = 1,
                explanation = "Attention evaluates Query against Keys to weight Values via softmax(QK^T / √d_k)V.",
                category = "Artificial Intelligence"
            ),
            TriviaQuestion(
                id = 5,
                question = "Which type of statistical error occurs when a true null hypothesis is incorrectly rejected?",
                urduQuestion = "جب سچی نل ہائپوتھیسز کو غلطی سے مسترد کر دیا جائے تو کون سی غلطی واقع ہوتی ہے؟",
                options = listOf("Type I Error (False Positive)", "Type II Error (False Negative)", "Sampling Bias", "Standard Error"),
                correctIndex = 0,
                explanation = "A Type I error (alpha) represents rejecting H0 when H0 is actually true.",
                category = "Research Methodology"
            )
        )
    }
}
