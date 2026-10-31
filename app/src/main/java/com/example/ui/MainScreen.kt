package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Extension
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.OndemandVideo
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.AssignmentsScreen
import com.example.ui.screens.BookReaderScreen
import com.example.ui.screens.LecturesScreen
import com.example.ui.screens.NotesScreen
import com.example.ui.screens.PuzzlesScreen
import com.example.ui.theme.CharcoalMuted
import com.example.ui.theme.CharcoalText
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldDivider
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PearlBackground
import com.example.ui.theme.PureWhite

enum class StudyTab(
    val title: String,
    val urduTitle: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val testTag: String
) {
    BOOK("Book", "کتاب", Icons.Filled.MenuBook, Icons.Outlined.MenuBook, "tab_book"),
    LECTURES("Lectures", "لیکچرز", Icons.Filled.OndemandVideo, Icons.Outlined.OndemandVideo, "tab_lectures"),
    ASSIGNMENTS("Assignments", "اسائنمنٹ", Icons.Filled.Assignment, Icons.Outlined.Assignment, "tab_assignments"),
    PUZZLES("Puzzles", "پزلز", Icons.Filled.Extension, Icons.Outlined.Extension, "tab_puzzles"),
    NOTES("Notes", "نوٹس", Icons.Filled.NoteAlt, Icons.Outlined.NoteAlt, "tab_notes")
}

@Composable
fun MainScreen(
    viewModel: MainStudyViewModel,
    modifier: Modifier = Modifier
) {
    var currentTab by remember { mutableStateOf(StudyTab.BOOK) }
    val selectedChapter by viewModel.selectedChapter.collectAsState()
    val selectedAssignment by viewModel.selectedAssignment.collectAsState()

    // Handle system back navigation across sub-screens
    BackHandler(enabled = currentTab != StudyTab.BOOK || selectedChapter != null || selectedAssignment != null) {
        when {
            selectedChapter != null -> viewModel.selectChapter(null)
            selectedAssignment != null -> viewModel.selectAssignment(null)
            currentTab != StudyTab.BOOK -> currentTab = StudyTab.BOOK
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(PearlBackground)
            .statusBarsPadding(),
        bottomBar = {
            // Hide bottom bar only when reading a chapter in detail for immersive view
            if (selectedChapter == null && selectedAssignment == null) {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(6.dp, spotColor = GoldBorder.copy(alpha = 0.5f))
                        .border(
                            1.dp,
                            GoldDivider,
                            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        )
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .navigationBarsPadding(),
                    containerColor = PureWhite,
                    tonalElevation = 2.dp
                ) {
                    StudyTab.entries.forEach { tab ->
                        val isSelected = currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentTab = tab },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) tab.filledIcon else tab.outlinedIcon,
                                    contentDescription = tab.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = tab.title,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = GoldDark,
                                selectedTextColor = GoldDark,
                                indicatorColor = GoldContainer,
                                unselectedIconColor = CharcoalMuted,
                                unselectedTextColor = CharcoalMuted
                            ),
                            modifier = Modifier.testTag(tab.testTag)
                        )
                    }
                }
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(targetState = currentTab, label = "tab_crossfade") { tab ->
                when (tab) {
                    StudyTab.BOOK -> BookReaderScreen(viewModel = viewModel)
                    StudyTab.LECTURES -> LecturesScreen(viewModel = viewModel)
                    StudyTab.ASSIGNMENTS -> AssignmentsScreen(viewModel = viewModel)
                    StudyTab.PUZZLES -> PuzzlesScreen(viewModel = viewModel)
                    StudyTab.NOTES -> NotesScreen(viewModel = viewModel)
                }
            }
        }
    }
}
