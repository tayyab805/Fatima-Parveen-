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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.StudyNote
import com.example.ui.MainStudyViewModel
import com.example.ui.components.GoldBorderCard
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryHeader
import com.example.ui.components.ShimmerBadge
import com.example.ui.theme.CharcoalFaint
import com.example.ui.theme.CharcoalMuted
import com.example.ui.theme.CharcoalText
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldDivider
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PearlBackground
import com.example.ui.theme.PureWhite

@Composable
fun NotesScreen(
    viewModel: MainStudyViewModel,
    modifier: Modifier = Modifier
) {
    val notes by viewModel.notes.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf("All") }
    var showCreateDialog by remember { mutableStateOf(false) }

    val tags = listOf("All", "Exam Prep", "Formulas", "AI Concepts", "Mathematics", "General")

    val filteredNotes = notes.filter { note ->
        val matchesSearch = searchQuery.isBlank() ||
                note.title.contains(searchQuery, ignoreCase = true) ||
                note.content.contains(searchQuery, ignoreCase = true) ||
                note.tag.contains(searchQuery, ignoreCase = true)
        val matchesTag = selectedTag == "All" || note.tag.equals(selectedTag, ignoreCase = true)
        matchesSearch && matchesTag
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PearlBackground)
            .testTag("notes_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                LuxuryHeader(
                    title = "SYLLABUS STUDY NOTES",
                    subtitle = "Personal formulas, exam takeaways, and chapter highlights",
                    urduTitle = "نصاب کے مطالعاتی نوٹس"
                )
            }

            // Search Bar
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search your notes... (نوٹس میں تلاش کریں)", color = CharcoalMuted, fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = GoldPrimary)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = PureWhite,
                            unfocusedContainerColor = PureWhite,
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Tag Chips Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tags.take(4).forEach { tag ->
                            val isSelected = selectedTag == tag
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable { selectedTag = tag }
                                    .border(1.dp, if (isSelected) GoldPrimary else GoldDivider, RoundedCornerShape(20.dp)),
                                color = if (isSelected) GoldContainer else PureWhite
                            ) {
                                Text(
                                    text = tag,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) GoldDark else CharcoalMuted,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${filteredNotes.size} Notes Found",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CharcoalMuted,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(filteredNotes, key = { it.id }) { note ->
                NoteCardItem(
                    note = note,
                    onTogglePin = { viewModel.togglePinNote(note) },
                    onDelete = { viewModel.deleteNote(note) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        // Floating Action Button to Add New Note
        FloatingActionButton(
            onClick = { showCreateDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 80.dp)
                .testTag("create_note_fab"),
            containerColor = GoldPrimary,
            contentColor = PureWhite,
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
        }

        if (showCreateDialog) {
            CreateNoteDialog(
                onDismiss = { showCreateDialog = false },
                onCreate = { title, content, tag ->
                    viewModel.addNote(title, content, tag)
                    showCreateDialog = false
                }
            )
        }
    }
}

@Composable
private fun NoteCardItem(
    note: StudyNote,
    onTogglePin: () -> Unit,
    onDelete: () -> Unit
) {
    GoldBorderCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        testTag = "note_item_${note.id}"
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ShimmerBadge(text = note.tag)
                    if (note.isPinned) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.PushPin,
                            contentDescription = "Pinned",
                            tint = GoldPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Row {
                    IconButton(
                        onClick = onTogglePin,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (note.isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                            contentDescription = "Pin Note",
                            tint = if (note.isPinned) GoldPrimary else CharcoalFaint,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Note",
                            tint = CharcoalFaint,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = CharcoalText,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CharcoalText,
                    lineHeight = 20.sp
                )
            )
        }
    }
}

@Composable
private fun CreateNoteDialog(
    onDismiss: () -> Unit,
    onCreate: (title: String, content: String, tag: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("Exam Prep") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "New Syllabus Note (نیا نوٹ بنائیں)",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = GoldDark,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Note Title") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = tag,
                    onValueChange = { tag = it },
                    label = { Text("Tag / Category (e.g. Formulas, Exam Prep)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Note Content") },
                    placeholder = { Text("Write formulas, definitions, key points...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 5
                )
            }
        },
        confirmButton = {
            GoldButton(
                text = "Save Note",
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        onCreate(title, content, tag)
                    }
                },
                enabled = title.isNotBlank() && content.isNotBlank()
            )
        },
        dismissButton = {
            GoldOutlinedButton(text = "Cancel", onClick = onDismiss)
        },
        containerColor = PureWhite
    )
}
