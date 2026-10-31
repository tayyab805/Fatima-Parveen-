package com.example.data.local

import com.example.data.model.AssignmentItem
import com.example.data.model.BookChapter
import com.example.data.model.LectureItem
import com.example.data.model.StudyNote

object InitialSyllabusData {

    fun getInitialChapters(): List<BookChapter> {
        return listOf(
            BookChapter(
                chapterNumber = 1,
                title = "Foundations of Computer Science & Algorithmic Logic",
                urduTitle = "کمپیوٹر سائنس اور الگورتھمک منطق کی بنیادیں",
                category = "Computer Science",
                estimatedMinutes = 18,
                summary = "An in-depth exploration of computational thinking, boolean algebra, sorting algorithms, and asymptotic complexity (Big-O notation).",
                fullContent = """
# Chapter 1: Foundations of Computer Science & Algorithmic Logic

## 1. Introduction to Computational Thinking
Computational thinking is an analytical approach to solving complex problems by breaking them down into manageable, algorithmic steps. Rather than simply writing code, computational thinking empowers scholars and scientists to conceptualize data structures and optimize system constraints.

### The Four Pillars:
1. Decomposition: Breaking a complicated problem into smaller, isolated components.
2. Pattern Recognition: Identifying recurring trends, similarities, and invariants across data points.
3. Abstraction: Isolating essential mechanisms while ignoring distracting implementation trivia.
4. Algorithm Design: Formulating unambiguous, deterministic rules to reach an optimal outcome.

---

## 2. Boolean Algebra and Digital Logic
Modern computing is predicated on the dualistic truth states established by George Boole:
* Conjunction (AND): Yields true only when both premises are strictly true.
* Disjunction (OR): Yields true when at least one premise is true.
* Negation (NOT): Inverts the Boolean truth state.
* Exclusive OR (XOR): True if and only if the inputs diverge.

### De Morgan's Laws:
* NOT (A AND B) = (NOT A) OR (NOT B)
* NOT (A OR B) = (NOT A) AND (NOT B)

These mathematical transformations form the backbone of binary arithmetic and logic gate synthesis in modern microarchitectures.

---

## 3. Asymptotic Complexity & The Big-O Hierarchy
Algorithm efficiency is benchmarked not in raw wall-clock seconds, but as a function of input scale n:
* O(1) Constant Time: Direct array indexing, hash map lookups.
* O(log n) Logarithmic Time: Binary search on ordered arrays.
* O(n) Linear Time: Single-pass traversals, linear scans.
* O(n log n) Linearithmic Time: Optimal comparison-based sorting (MergeSort, HeapSort).
* O(n^2) Quadratic Time: Nested comparisons (BubbleSort, SelectionSort).
* O(2^n) Exponential Time: Brute-force combinatorial optimization (Subset Sum, Traveling Salesperson).

---

## 4. Sorting Paradigms
### Merge Sort (Divide & Conquer):
Recursively bisects the array into halves, sorts each half independently, and combines the sorted sequences in linear time.
* Worst-Case Time: O(n log n)
* Auxiliary Memory: O(n)

### Quick Sort (Partitioning):
Selects a pivot element, partitions elements into values less than and greater than the pivot, and recurses.
* Average-Case Time: O(n log n)
* Worst-Case Time: O(n^2) (mitigated via randomized pivot selection).

---

## 5. Chapter Summary & Core Axiom
Efficiency in software architecture stems from choosing the optimal data structure before writing a single line of algorithmic logic.
                """.trimIndent(),
                keyFormulasAndTerms = "Big-O Notation, MergeSort O(n log n), De Morgan's Laws, Divide & Conquer, Binary Trees, P vs NP",
                progressPercent = 35
            ),
            BookChapter(
                chapterNumber = 2,
                title = "Natural Sciences: Energy, Thermodynamics & Molecular Dynamics",
                urduTitle = "قدرتی سائنس: توانائی، حرکیات اور سالماتی نظام",
                category = "Physics & Chemistry",
                estimatedMinutes = 20,
                summary = "Mastery of thermodynamic laws, entropy, conservation of energy, reaction kinetics, and fundamental physical equilibria.",
                fullContent = """
# Chapter 2: Energy, Thermodynamics & Molecular Dynamics

## 1. The Fundamental Laws of Thermodynamics
The macroscopic behavior of matter and energy in physical chemistry and engineering is governed by four foundational laws:

### The Zeroth Law (Thermal Equilibrium):
If body A is in thermal equilibrium with body B, and body B is in thermal equilibrium with body C, then A and C are mutually in equilibrium (Ta = Tb = Tc). This establishes the validity of temperature as a state variable.

### The First Law (Conservation of Energy):
Energy can neither be created nor annihilated; it can only change its form:
ΔU = Q - W
Where ΔU is internal energy change, Q is heat added to the system, and W is mechanical work executed by the system.

### The Second Law (Universal Entropy):
In any spontaneous process within an isolated system, the total entropy (ΔS) always increases:
ΔS_universe >= 0
No heat engine can achieve 100% thermal efficiency without exhausting waste heat to a colder reservoir (Carnot limit).

### The Third Law (Absolute Zero):
As the thermodynamic temperature of a perfect crystalline structure approaches absolute zero (0 K or -273.15 C), its entropy approaches zero (S -> 0).

---

## 2. Gibbs Free Energy & Reaction Spontaneity
To predict whether a biochemical or chemical reaction will proceed spontaneously at constant temperature and pressure, we utilize Josiah Willard Gibbs' criterion:
ΔG = ΔH - T*ΔS
* ΔG < 0 (Exergonic): Spontaneous, thermodynamic driving force present.
* ΔG > 0 (Endergonic): Non-spontaneous; requires coupled input of external energy (e.g., ATP hydrolysis).
* ΔG = 0: Chemical equilibrium achieved (K_eq).

---

## 3. Kinetic Theory & Activation Barriers
The Arrhenius equation describes how chemical reaction rate constants (k) scale exponentially with temperature:
k = A * e^(-E_a / (R*T))
Catalysts accelerate chemical kinetics by providing an alternative reaction pathway featuring a lower activation energy (E_a), without perturbing the fundamental equilibrium position (ΔG).
                """.trimIndent(),
                keyFormulasAndTerms = "ΔU = Q - W, ΔG = ΔH - T*ΔS, S = k_B ln(Ω), Arrhenius Equation, Carnot Efficiency",
                progressPercent = 10
            ),
            BookChapter(
                chapterNumber = 3,
                title = "Mathematics: Differential & Integral Calculus Fundamentals",
                urduTitle = "ریاضی: تفریقی اور تکمیلی حساب کے بنیادی اصول",
                category = "Mathematics",
                estimatedMinutes = 22,
                summary = "Rigorous foundation in real limits, continuity, derivative rates of change, optimization, integration, and the Fundamental Theorem of Calculus.",
                fullContent = """
# Chapter 3: Differential & Integral Calculus Fundamentals

## 1. Limits and the Formal Definition of the Derivative
Calculus translates the language of static geometry into continuous change and physical flux.

### The Epsilon-Delta Limit Criterion:
We state that limit as x approaches c of f(x) = L if for every epsilon > 0, there exists a delta > 0 such that:
0 < |x - c| < delta implies |f(x) - L| < epsilon

### Instantaneous Rate of Change:
The first derivative f'(x) represents the slope of the tangent line:
f'(x) = limit as h approaches 0 of [f(x + h) - f(x)] / h

---

## 2. Core Operational Differentiation Rules
* Power Rule: d/dx [x^n] = n * x^(n-1)
* Product Rule: d/dx [u * v] = u'*v + u*v'
* Quotient Rule: d/dx [u / v] = (u'*v - u*v') / v^2
* Chain Rule: d/dx [f(g(x))] = f'(g(x)) * g'(x)

---

## 3. Optimization and Critical Extremities
To identify local maxima and minima:
1. Compute the first derivative f'(x) = 0 or find where f'(x) is undefined.
2. Evaluate second derivative f''(x):
   * If f''(x) > 0, the curve is concave upward (Local Minimum).
   * If f''(x) < 0, the curve is concave downward (Local Maximum).
   * If f''(x) = 0, test for an inflection point.

---

## 4. The Fundamental Theorem of Calculus (FTC)
The FTC forms the monumental bridge reconciling differentiation with accumulation:

### Part 1:
If f is continuous on [a, b] and F(x) = integral from a to x of f(t) dt, then:
F'(x) = f(x)

### Part 2:
Integral from a to b of f(x) dx = F(b) - F(a)
Where F is any antiderivative of f.
                """.trimIndent(),
                keyFormulasAndTerms = "d/dx[x^n]=nx^(n-1), Product & Chain Rules, ∫f(x)dx=F(b)-F(a), Taylor Series, Critical Points",
                progressPercent = 0
            ),
            BookChapter(
                chapterNumber = 4,
                title = "Artificial Intelligence & Deep Neural Architectures",
                urduTitle = "مصنوعی ذہانت اور گہری نیورل ساختیں",
                category = "Computer Science & AI",
                estimatedMinutes = 25,
                summary = "Exploration of perceptrons, backpropagation, gradient descent optimization, attention mechanisms, and Transformer architectures.",
                fullContent = """
# Chapter 4: Artificial Intelligence & Deep Neural Architectures

## 1. Biological Analogy to Artificial Perceptrons
An artificial neuron aggregates a weighted sum of inputs and passes the result through a non-linear activation function:
y = sigma( sum(w_i * x_i) + b )
Where w_i represents synaptic weights, x_i inputs, b bias, and sigma non-linearity.

### Essential Activation Functions:
* Sigmoid: sigma(z) = 1 / (1 + e^(-z)) (Bounded between 0 and 1)
* ReLU (Rectified Linear Unit): f(z) = max(0, z) (Prevents vanishing gradients)
* GELU & Swish: Smooth approximations standard in state-of-the-art Transformer models.

---

## 2. Gradient Descent & Error Backpropagation
Supervised learning trains neural network weights by minimizing a loss function L(theta):
theta_{t+1} = theta_t - eta * grad(L)
Where eta is the learning rate.

The chain rule of multivariate calculus is executed in reverse (backpropagation) to calculate the precise partial derivative dL / dw_ij for every synaptic weight in arbitrary deep layers.

---

## 3. The Transformer & Self-Attention Revolution
Modern generative language models leverage the scaled dot-product attention mechanism:
Attention(Q, K, V) = softmax( (Q * K^T) / sqrt(d_k) ) * V
* Q (Query): What the current token is seeking.
* K (Key): What other tokens offer as contextual tags.
* V (Value): The actual semantic representation conveyed.

By liberating neural networks from sequential recurrence (RNNs/LSTMs), transformers enable massive parallelization across distributed GPUs and TPU clusters.
                """.trimIndent(),
                keyFormulasAndTerms = "Attention(Q,K,V)=softmax(QK^T/√d_k)V, Backpropagation, Cross-Entropy Loss, Gradient Descent, Transformers",
                progressPercent = 0
            ),
            BookChapter(
                chapterNumber = 5,
                title = "Scientific Research Methodology & Academic Synthesis",
                urduTitle = "تحقیقی طریقہ کار اور علمی ترکیب و تحریر",
                category = "Research & Ethics",
                estimatedMinutes = 15,
                summary = "Scientific inquiry, hypothesis formulation, peer-review standards, statistical validation (p-values, ANOVA), and ethical research integrity.",
                fullContent = """
# Chapter 5: Research Methodology & Academic Synthesis

## 1. The Epistemology of Scientific Inquiry
The scientific method is a self-correcting epistemological framework:
1. Observation & Literature Review: Exhaustive synthesis of prior foundational publications.
2. Hypothesis Formulation (H0 vs H1): Establishing falsifiable, testable predictions.
3. Controlled Experimentation: Isolating independent variables while holding confounding variables strictly invariant.
4. Data Acquisition & Statistical Rigor: Measuring effect size, confidence intervals, and statistical significance (p < 0.05).
5. Peer Review & Replication: Subjecting methodology to independent international scrutiny.

---

## 2. Statistical Inferences & Hypothesis Testing
* Null Hypothesis (H0): Assumes no true effect or divergence exists between sample groups.
* Alternative Hypothesis (H1): Posits a statistically meaningful correlation or causal relationship.
* Type I Error (alpha): False positive (rejecting a true null hypothesis).
* Type II Error (beta): False negative (failing to reject a false null hypothesis).

---

## 3. Academic Synthesis and Scholarly Ethics
Ethical scholarship requires unwavering attribution. Plagiarism is strictly avoided by mastering paraphrasing, intellectual synthesis, and standard citation frameworks (IEEE, APA, Harvard).
                """.trimIndent(),
                keyFormulasAndTerms = "Null Hypothesis (H0), Type I & II Errors, Statistical Power (1-β), Peer Review, APA/IEEE Citation",
                progressPercent = 0
            )
        )
    }

    fun getInitialNotes(): List<StudyNote> {
        return listOf(
            StudyNote(
                chapterId = 1,
                title = "Big-O Quick Memorization Sheet",
                content = "Always choose MergeSort for predictable O(n log n). Remember: Hash tables are O(1) on average, but can degrade to O(n) during catastrophic hash collisions.",
                tag = "Exam Prep",
                isPinned = true
            ),
            StudyNote(
                chapterId = 2,
                title = "Thermodynamics Formula Review",
                content = "ΔG = ΔH - T*ΔS. If ΔG is negative, it's spontaneous! If ΔG is positive, it needs energy input. Temperature must ALWAYS be in Kelvin (K = C + 273.15).",
                tag = "Formulas",
                isPinned = true
            ),
            StudyNote(
                chapterId = 3,
                title = "Calculus Integration Cheat Sheet",
                content = "Remember the fundamental theorem: Derivative of an integral returns the original function! Integral of x^n is x^(n+1)/(n+1) + C.",
                tag = "Mathematics",
                isPinned = false
            ),
            StudyNote(
                chapterId = 4,
                title = "Attention Mechanism Mental Model",
                content = "Q (Query), K (Key), V (Value). Like searching in a library: Query is your topic, Key is book spine labels, Value is the book content inside.",
                tag = "AI Concepts",
                isPinned = false
            )
        )
    }

    fun getInitialLectures(): List<LectureItem> {
        return listOf(
            LectureItem(
                chapterNumber = 1,
                lectureNumber = 1,
                title = "Lecture 1: Computational Thinking & Algorithmic Complexity",
                urduTitle = "لیکچر ۱: کمپیوٹیشنل سوچ اور الگورتھمک پیچیدگی",
                instructor = "Dr. Tariq Al-Hassan",
                durationMinutes = 45,
                summary = "Detailed breakdown of Big-O, Big-Omega, and Big-Theta notations with real-world searching demonstrations.",
                keyTakeaways = "1. Why constant factors are dropped in asymptotic analysis\n2. Worst vs average case bounds\n3. Binary Search recursive tree visualization",
                isCompleted = true,
                studentNotes = "Reviewed binary search tree recursion depth. Need to practice logarithmic proofs."
            ),
            LectureItem(
                chapterNumber = 1,
                lectureNumber = 2,
                title = "Lecture 2: Sorting Algorithms & Divide and Conquer",
                urduTitle = "لیکچر ۲: سارٹنگ کے الگورتھمز اور ڈیوائیڈ اینڈ کانکر",
                instructor = "Dr. Tariq Al-Hassan",
                durationMinutes = 50,
                summary = "Implementation of MergeSort and QuickSort with recursion stack visualization and memory tradeoffs.",
                keyTakeaways = "1. Merge step invariant\n2. In-place partitioning in QuickSort\n3. Lower bound for comparison sorting: Ω(n log n)",
                isCompleted = false,
                studentNotes = ""
            ),
            LectureItem(
                chapterNumber = 2,
                lectureNumber = 3,
                title = "Lecture 3: Laws of Thermodynamics & System Boundaries",
                urduTitle = "لیکچر ۳: تھرموڈینامکس کے قوانین اور باؤنڈریز",
                instructor = "Prof. Fatima Zahra",
                durationMinutes = 55,
                summary = "Open, closed, and isolated systems. Derivation of the First and Second laws of thermodynamics.",
                keyTakeaways = "1. Sign conventions for heat and work\n2. Reversible vs irreversible pathways\n3. Carnot engine theoretical limit",
                isCompleted = false,
                studentNotes = ""
            ),
            LectureItem(
                chapterNumber = 3,
                lectureNumber = 4,
                title = "Lecture 4: Differential Calculus & Optimization Geometry",
                urduTitle = "لیکچر ۴: تفریقی حساب اور اصلاحی جیومیٹری",
                instructor = "Dr. Arshad Khan",
                durationMinutes = 40,
                summary = "Finding maxima and minima in applied problems using first and second derivative tests.",
                keyTakeaways = "1. Critical point evaluation\n2. Concavity and inflection points\n3. Real-world cost minimization modeling",
                isCompleted = false,
                studentNotes = ""
            ),
            LectureItem(
                chapterNumber = 4,
                lectureNumber = 5,
                title = "Lecture 5: Neural Networks & The Attention Mechanism",
                urduTitle = "لیکچر ۵: نیورل نیٹ ورکس اور اٹینشن کا طریقہ کار",
                instructor = "Dr. Sarah Mansoor",
                durationMinutes = 60,
                summary = "From single perceptrons to multi-head self-attention in large language models.",
                keyTakeaways = "1. Activation functions: ReLU vs GELU\n2. Softmax temperature scaling\n3. Matrix dot-product attention calculation",
                isCompleted = false,
                studentNotes = ""
            )
        )
    }

    fun getInitialAssignments(): List<AssignmentItem> {
        return listOf(
            AssignmentItem(
                title = "Assignment 1: Compare Sorting Complexities & Recurrence",
                subject = "Computer Science",
                promptQuestion = "Explain why Merge Sort has a guaranteed time complexity of O(n log n) in all cases, whereas Quick Sort can degrade to O(n^2). Provide the recurrence relation for both and describe how randomized pivot selection prevents worst-case degradation.",
                studentDraft = "Merge sort divides the array into two halves recursively...",
                aiAnswer = "",
                status = "Pending",
                dueDate = "Nov 15, 2026"
            ),
            AssignmentItem(
                title = "Assignment 2: Free Energy & Thermodynamic Spontaneity",
                subject = "Natural Sciences",
                promptQuestion = "Under what temperature conditions will an endothermic reaction (ΔH > 0) with positive entropy change (ΔS > 0) occur spontaneously? Express the relationship mathematically using Gibbs Free Energy and provide a real-world chemical example.",
                studentDraft = "",
                aiAnswer = "",
                status = "Pending",
                dueDate = "Nov 20, 2026"
            ),
            AssignmentItem(
                title = "Assignment 3: Optimization of a Cylindrical Storage Tank",
                subject = "Mathematics",
                promptQuestion = "A closed cylindrical container must hold a fixed volume V = 1000 cubic cm. Using differential calculus, find the exact ratio of height h to radius r that minimizes the total surface area of the metal required. Show all derivative steps and the second derivative verification.",
                studentDraft = "",
                aiAnswer = "",
                status = "Pending",
                dueDate = "Nov 25, 2026"
            )
        )
    }
}
