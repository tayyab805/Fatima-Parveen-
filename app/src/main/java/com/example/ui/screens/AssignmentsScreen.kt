package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AssignmentItem
import com.example.ui.AiSolveState
import com.example.ui.MainStudyViewModel
import com.example.ui.components.GoldBorderCard
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldOrnamentalDivider
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryHeader
import com.example.ui.components.ShimmerBadge
import com.example.ui.theme.CharcoalMuted
import com.example.ui.theme.CharcoalText
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldDivider
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PearlBackground
import com.example.ui.theme.PureWhite

@Composable
fun AssignmentsScreen(
    viewModel: MainStudyViewModel,
    modifier: Modifier = Modifier
) {
    val assignments by viewModel.assignments.collectAsState()
    val selectedAssignment by viewModel.selectedAssignment.collectAsState()
    val aiSolveState by viewModel.aiSolveState.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }

    if (selectedAssignment != null) {
        AssignmentDetailAndSolverView(
            assignment = selectedAssignment!!,
            aiSolveState = aiSolveState,
            onBack = { viewModel.selectAssignment(null) },
            onSolveWithAi = { viewModel.solveAssignmentWithAi(selectedAssignment!!) },
            onUpdateDraft = { draft -> viewModel.updateStudentDraft(selectedAssignment!!, draft) },
            onInsertAiToDraft = { answer -> viewModel.insertAiAnswerToDraft(selectedAssignment!!, answer) }
        )
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(PearlBackground)
                .testTag("assignments_screen")
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                item {
                    LuxuryHeader(
                        title = "ASSIGNMENTS & AI SOLVER",
                        subtitle = "Write, manage, and solve homework & syllabus assignments with Gemini AI",
                        urduTitle = "اسائنمنٹس اور اے آئی حل"
                    )
                }

                // AI Solver Showcase Banner
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp)
                            .border(1.2.dp, GoldBorder, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        color = GoldContainer
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(GoldPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = PureWhite,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "AI Academic Assignment Solver",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = GoldDark,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "Write your assignment question, tap 'Solve with AI', and get a step-by-step academic answer formatted for your syllabus.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = CharcoalText,
                                        lineHeight = 17.sp
                                    )
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Current Assignments (${assignments.size})",
                        modifier = Modifier.padding(horizontal = 20.dp),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = CharcoalMuted,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(assignments, key = { it.id }) { assignment ->
                    AssignmentCardItem(
                        assignment = assignment,
                        onClick = { viewModel.selectAssignment(assignment) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Floating Action Button to Add New Assignment
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 80.dp)
                    .testTag("create_assignment_fab"),
                containerColor = GoldPrimary,
                contentColor = PureWhite,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Assignment")
            }

            if (showCreateDialog) {
                CreateAssignmentDialog(
                    onDismiss = { showCreateDialog = false },
                    onCreate = { title, subject, prompt, dueDate ->
                        viewModel.addAssignment(title, subject, prompt, dueDate)
                        showCreateDialog = false
                    }
                )
            }
        }
    }
}

@Composable
private fun AssignmentCardItem(
    assignment: AssignmentItem,
    onClick: () -> Unit
) {
    GoldBorderCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        onClick = onClick,
        testTag = "assignment_item_${assignment.id}"
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ShimmerBadge(text = assignment.subject)
                Text(
                    text = "Due: ${assignment.dueDate.ifBlank { "Flexible" }}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CharcoalMuted,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = assignment.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = CharcoalText,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = assignment.promptQuestion,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = CharcoalMuted,
                    lineHeight = 18.sp
                ),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (assignment.aiAnswer.isNotBlank()) {
                        Surface(
                            color = GoldContainer,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = GoldDark,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "AI Solved ✓",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = GoldDark,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "Drafting...",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = CharcoalMuted
                            )
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Open & Solve →",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun AssignmentDetailAndSolverView(
    assignment: AssignmentItem,
    aiSolveState: AiSolveState,
    onBack: () -> Unit,
    onSolveWithAi: () -> Unit,
    onUpdateDraft: (String) -> Unit,
    onInsertAiToDraft: (String) -> Unit
) {
    BackHandler { onBack() }
    val context = LocalContext.current
    var studentDraftText by remember(assignment.id) { mutableStateOf(assignment.studentDraft) }
    var isDraftSaved by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PearlBackground)
            .testTag("assignment_detail_view")
    ) {
        // Top Navigation Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = PureWhite,
            shadowElevation = 2.dp,
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldDivider)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldDark
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = assignment.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = CharcoalText,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1
                    )
                    Text(
                        text = assignment.subject,
                        style = MaterialTheme.typography.bodySmall.copy(color = GoldDark)
                    )
                }
            }
        }

        // Main Scrolling Workspace
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Assignment Question Card
            GoldBorderCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Question / Problem Statement",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = GoldDark,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        ShimmerBadge(text = assignment.subject)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = assignment.promptQuestion,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = CharcoalText,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 22.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // AI Solver Action Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🤖 AI Assignment Solver",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GoldDark,
                        fontWeight = FontWeight.Bold
                    )
                )

                GoldButton(
                    text = if (aiSolveState is AiSolveState.Solving) "Thinking..." else "Solve with AI (حل کریں)",
                    onClick = onSolveWithAi,
                    icon = Icons.Default.AutoAwesome,
                    enabled = aiSolveState !is AiSolveState.Solving,
                    testTag = "solve_with_ai_button",
                    modifier = Modifier.height(42.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // AI Solver Output Section
            when (aiSolveState) {
                is AiSolveState.Solving -> {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, GoldBorder, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        color = PureWhite
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                color = GoldPrimary,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Gemini AI is analyzing syllabus axioms & deriving academic answer...",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = GoldDark,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
                is AiSolveState.Success -> {
                    val answerText = aiSolveState.answer
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.2.dp, GoldPrimary, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        color = PureWhite
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "AI Academic Solution (اے آئی کا جواب)",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = GoldDark,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }

                                Row {
                                    IconButton(
                                        onClick = {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            clipboard.setPrimaryClip(ClipData.newPlainText("AI Answer", answerText))
                                            Toast.makeText(context, "AI Answer copied to clipboard!", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy",
                                            tint = GoldPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = answerText,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = CharcoalText,
                                    lineHeight = 22.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // 1-Click Insert to Draft Button
                            GoldOutlinedButton(
                                text = "Insert into My Draft (میرے ڈرافٹ میں شامل کریں)",
                                onClick = {
                                    studentDraftText = if (studentDraftText.isBlank()) {
                                        answerText
                                    } else {
                                        "$studentDraftText\n\n--- AI Academic Synthesis ---\n$answerText"
                                    }
                                    onUpdateDraft(studentDraftText)
                                    Toast.makeText(context, "Inserted into your draft below!", Toast.LENGTH_SHORT).show()
                                },
                                icon = Icons.Default.PostAdd,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
                is AiSolveState.Error -> {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFD32F2F), RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        color = PureWhite
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Unable to Generate AI Solution",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color(0xFFD32F2F),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = aiSolveState.message,
                                style = MaterialTheme.typography.bodySmall.copy(color = CharcoalText)
                            )
                        }
                    }
                }
                AiSolveState.Idle -> {
                    // Nothing yet, ready to solve
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            GoldOrnamentalDivider()

            // Student Draft Workspace Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✍️ My Submission & Draft (میرا جواب)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GoldDark,
                        fontWeight = FontWeight.Bold
                    )
                )

                GoldButton(
                    text = if (isDraftSaved) "Saved ✓" else "Save Draft",
                    onClick = {
                        onUpdateDraft(studentDraftText)
                        isDraftSaved = true
                        Toast.makeText(context, "Assignment draft saved!", Toast.LENGTH_SHORT).show()
                    },
                    icon = Icons.Default.Save,
                    modifier = Modifier.height(38.dp),
                    testTag = "save_draft_button"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = studentDraftText,
                onValueChange = {
                    studentDraftText = it
                    isDraftSaved = false
                },
                placeholder = {
                    Text(
                        text = "Write your assignment answer here, or click 'Solve with AI' above to generate solutions to incorporate...",
                        color = CharcoalMuted
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .testTag("student_draft_input"),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = PureWhite,
                    unfocusedContainerColor = PureWhite,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = GoldBorder
                )
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun CreateAssignmentDialog(
    onDismiss: () -> Unit,
    onCreate: (title: String, subject: String, prompt: String, dueDate: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("Computer Science") }
    var prompt by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("Next Week") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "New Syllabus Assignment (نئی اسائنمنٹ)",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = GoldDark,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Assignment Title") },
                    placeholder = { Text("e.g. Asymptotic Proof of MergeSort") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject / Course") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = prompt,
                    onValueChange = { prompt = it },
                    label = { Text("Assignment Question / Problem Statement") },
                    placeholder = { Text("Paste your syllabus question here...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 5
                )

                OutlinedTextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Due Date") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            GoldButton(
                text = "Create Assignment",
                onClick = {
                    if (title.isNotBlank() && prompt.isNotBlank()) {
                        onCreate(title, subject, prompt, dueDate)
                    }
                },
                enabled = title.isNotBlank() && prompt.isNotBlank()
            )
        },
        dismissButton = {
            GoldOutlinedButton(text = "Cancel", onClick = onDismiss)
        },
        containerColor = PureWhite
    )
}
