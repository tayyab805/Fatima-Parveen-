package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LectureItem
import com.example.ui.MainStudyViewModel
import com.example.ui.components.GoldBorderCard
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryHeader
import com.example.ui.components.ShimmerBadge
import com.example.ui.theme.CharcoalMuted
import com.example.ui.theme.CharcoalText
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldDivider
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.GoldShimmerBrush
import com.example.ui.theme.PearlBackground
import com.example.ui.theme.PureWhite

@Composable
fun LecturesScreen(
    viewModel: MainStudyViewModel,
    modifier: Modifier = Modifier
) {
    val lectures by viewModel.lectures.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") } // "All", "Completed", "Pending"
    var showAddDialog by remember { mutableStateOf(false) }

    val completedCount = lectures.count { it.isCompleted }
    val totalCount = lectures.size
    val progressFraction = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

    val filteredLectures = when (selectedFilter) {
        "Completed" -> lectures.filter { it.isCompleted }
        "Pending" -> lectures.filter { !it.isCompleted }
        else -> lectures
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PearlBackground)
            .testTag("lectures_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                LuxuryHeader(
                    title = "SYLLABUS LECTURES",
                    subtitle = "Track academic lecture recordings, key takeaways, and personal notes",
                    urduTitle = "لیکچرز کی فہرست و نوٹس"
                )
            }

            // Progress Banner Card
            item {
                GoldBorderCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    testTag = "lecture_progress_card"
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Lectures Progress (پیشرفت)",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = GoldDark,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$completedCount of $totalCount Lectures Finished",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = CharcoalText,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Text(
                                text = "${(progressFraction * 100).toInt()}% of curriculum watched",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = CharcoalMuted
                                )
                            )
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(64.dp)
                        ) {
                            CircularProgressIndicator(
                                progress = { progressFraction },
                                modifier = Modifier.fillMaxSize(),
                                color = GoldPrimary,
                                trackColor = GoldDivider,
                                strokeWidth = 6.dp
                            )
                            Text(
                                text = "${(progressFraction * 100).toInt()}%",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = GoldDark,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

            // Filter Chips
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf("All", "Pending", "Completed").forEach { filterName ->
                        val isSelected = selectedFilter == filterName
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { selectedFilter = filterName }
                                .border(
                                    1.dp,
                                    if (isSelected) GoldPrimary else GoldDivider,
                                    RoundedCornerShape(20.dp)
                                ),
                            color = if (isSelected) GoldContainer else PureWhite
                        ) {
                            Text(
                                text = when (filterName) {
                                    "All" -> "All ($totalCount)"
                                    "Pending" -> "Pending (${totalCount - completedCount})"
                                    else -> "Completed ($completedCount)"
                                },
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isSelected) GoldDark else CharcoalMuted,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

            // Lecture Items
            items(filteredLectures, key = { it.id }) { lecture ->
                LectureCardItem(
                    lecture = lecture,
                    onToggleComplete = { viewModel.toggleLectureCompleted(lecture) },
                    onSaveNotes = { notes -> viewModel.updateLectureNotes(lecture, notes) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Floating Action Button to Add New Lecture
        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 80.dp)
                .testTag("add_lecture_fab"),
            containerColor = GoldPrimary,
            contentColor = PureWhite,
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Lecture")
        }

        if (showAddDialog) {
            AddLectureDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { title, urdu, instructor, duration, summary, takeaways ->
                    viewModel.addCustomLecture(title, urdu, instructor, duration, summary, takeaways)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
private fun LectureCardItem(
    lecture: LectureItem,
    onToggleComplete: () -> Unit,
    onSaveNotes: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var notesText by remember { mutableStateOf(lecture.studentNotes) }
    var isEditingNotes by remember { mutableStateOf(false) }

    GoldBorderCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        testTag = "lecture_card_${lecture.lectureNumber}"
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Lecture Number Badge
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (lecture.isCompleted) GoldPrimary else GoldContainer)
                        .border(1.2.dp, GoldPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "L${lecture.lectureNumber}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (lecture.isCompleted) PureWhite else GoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = lecture.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = CharcoalText,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 20.sp
                        )
                    )
                    Text(
                        text = lecture.urduTitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = GoldDark,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                IconButton(
                    onClick = onToggleComplete,
                    modifier = Modifier.testTag("toggle_lecture_${lecture.lectureNumber}")
                ) {
                    Icon(
                        imageVector = if (lecture.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = "Toggle Complete",
                        tint = if (lecture.isCompleted) GoldPrimary else CharcoalMuted,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Metadata row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = GoldDark,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = lecture.instructor,
                        style = MaterialTheme.typography.bodySmall.copy(color = CharcoalMuted)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = null,
                        tint = GoldDark,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${lecture.durationMinutes} mins",
                        style = MaterialTheme.typography.bodySmall.copy(color = CharcoalMuted)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = lecture.summary,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CharcoalText,
                    lineHeight = 19.sp
                )
            )

            // Expandable details (Key Takeaways & Notes)
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    HorizontalDivider(color = GoldDivider, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "📌 Key Takeaways & Discussion",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = lecture.keyTakeaways,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = CharcoalText,
                            lineHeight = 18.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Student Personal Notes on Lecture
                    Text(
                        text = "✍️ My Personal Lecture Notes",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    if (isEditingNotes) {
                        OutlinedTextField(
                            value = notesText,
                            onValueChange = { notesText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = PureWhite,
                                unfocusedContainerColor = PureWhite,
                                focusedBorderColor = GoldPrimary,
                                unfocusedBorderColor = GoldBorder
                            ),
                            placeholder = { Text("Write personal notes on this lecture...") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            GoldOutlinedButton(
                                text = "Cancel",
                                onClick = {
                                    notesText = lecture.studentNotes
                                    isEditingNotes = false
                                },
                                modifier = Modifier.height(36.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            GoldButton(
                                text = "Save Notes",
                                onClick = {
                                    onSaveNotes(notesText)
                                    isEditingNotes = false
                                },
                                modifier = Modifier.height(36.dp)
                            )
                        }
                    } else {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, GoldDivider, RoundedCornerShape(10.dp))
                                .clickable { isEditingNotes = true },
                            color = GoldContainer.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (notesText.isNotBlank()) notesText else "Tap to add your own study notes for this lecture...",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = if (notesText.isNotBlank()) CharcoalText else CharcoalMuted,
                                        fontStyle = if (notesText.isBlank()) androidx.compose.ui.text.font.FontStyle.Italic else androidx.compose.ui.text.font.FontStyle.Normal
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Default.EditNote,
                                    contentDescription = "Edit Note",
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Expand / Collapse Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isExpanded) "Show Less" else "View Takeaways & Notes",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GoldDark,
                        fontWeight = FontWeight.Bold
                    )
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = GoldDark,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun AddLectureDialog(
    onDismiss: () -> Unit,
    onAdd: (title: String, urdu: String, instructor: String, duration: Int, summary: String, takeaways: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var urduTitle by remember { mutableStateOf("") }
    var instructor by remember { mutableStateOf("") }
    var durationText by remember { mutableStateOf("45") }
    var summary by remember { mutableStateOf("") }
    var takeaways by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add Syllabus Lecture (لیکچر شامل کریں)",
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Lecture Title (English)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = urduTitle,
                    onValueChange = { urduTitle = it },
                    label = { Text("Urdu Title (لیکچر کا عنوان)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = instructor,
                        onValueChange = { instructor = it },
                        label = { Text("Instructor") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = durationText,
                        onValueChange = { durationText = it },
                        label = { Text("Minutes") },
                        modifier = Modifier.width(100.dp),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
                OutlinedTextField(
                    value = summary,
                    onValueChange = { summary = it },
                    label = { Text("Topics Covered / Summary") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            GoldButton(
                text = "Add Lecture",
                onClick = {
                    if (title.isNotBlank()) {
                        val duration = durationText.toIntOrNull() ?: 45
                        onAdd(title, urduTitle, instructor, duration, summary, takeaways)
                    }
                },
                enabled = title.isNotBlank()
            )
        },
        dismissButton = {
            GoldOutlinedButton(text = "Cancel", onClick = onDismiss)
        },
        containerColor = PureWhite
    )
}
