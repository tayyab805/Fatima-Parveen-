package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.BookChapter
import com.example.ui.MainStudyViewModel
import com.example.ui.components.GoldBorderCard
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldOrnamentalDivider
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryHeader
import com.example.ui.components.ShimmerBadge
import com.example.ui.theme.CharcoalFaint
import com.example.ui.theme.CharcoalMuted
import com.example.ui.theme.CharcoalText
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldDivider
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.GoldShimmerBrush
import com.example.ui.theme.PearlBackground
import com.example.ui.theme.PearlCard
import com.example.ui.theme.PureWhite

@Composable
fun BookReaderScreen(
    viewModel: MainStudyViewModel,
    modifier: Modifier = Modifier
) {
    val chapters by viewModel.chapters.collectAsState()
    val selectedChapter by viewModel.selectedChapter.collectAsState()
    val isTtsPlaying by viewModel.isTtsPlaying.collectAsState()

    if (selectedChapter != null) {
        ChapterDetailView(
            chapter = selectedChapter!!,
            isTtsPlaying = isTtsPlaying,
            onBack = { viewModel.selectChapter(null) },
            onToggleBookmark = { viewModel.toggleBookmark(selectedChapter!!) },
            onToggleTts = { viewModel.speakText(selectedChapter!!.fullContent) },
            onUpdateProgress = { p -> viewModel.updateChapterProgress(selectedChapter!!, p) }
        )
    } else {
        ChapterListView(
            chapters = chapters,
            onChapterClick = { viewModel.selectChapter(it) },
            onToggleBookmark = { viewModel.toggleBookmark(it) },
            modifier = modifier
        )
    }
}

@Composable
private fun ChapterListView(
    chapters: List<BookChapter>,
    onChapterClick: (BookChapter) -> Unit,
    onToggleBookmark: (BookChapter) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showOnlyBookmarked by remember { mutableStateOf(false) }

    val filteredChapters = chapters.filter { chapter ->
        val matchesSearch = searchQuery.isBlank() ||
                chapter.title.contains(searchQuery, ignoreCase = true) ||
                chapter.urduTitle.contains(searchQuery, ignoreCase = true) ||
                chapter.summary.contains(searchQuery, ignoreCase = true) ||
                chapter.keyFormulasAndTerms.contains(searchQuery, ignoreCase = true)
        val matchesBookmark = !showOnlyBookmarked || chapter.isBookmarked
        matchesSearch && matchesBookmark
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PearlBackground)
            .testTag("chapter_list_view"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Hero Banner Art
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gold_study_banner_1793428041564),
                    contentDescription = "Gold Study Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Translucent gold gradient scrim
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.2f),
                                    Color.Black.copy(alpha = 0.65f)
                                )
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "نور مطالعہ • نصاب کی کتاب",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldAccent,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "THE MASTER SYLLABUS BOOK",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = PureWhite,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "Complete curriculum notes, formulas, and academic theory",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFF3E5AB)
                        )
                    )
                }
            }
        }

        // Search Bar & Filter
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "Search chapters, formulas, or topics... (تلاش کریں)",
                            color = CharcoalMuted,
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = GoldPrimary
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("chapter_search_input"),
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${filteredChapters.size} Chapters in Syllabus",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = CharcoalMuted,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { showOnlyBookmarked = !showOnlyBookmarked }
                            .border(1.dp, if (showOnlyBookmarked) GoldPrimary else GoldDivider, RoundedCornerShape(20.dp)),
                        color = if (showOnlyBookmarked) GoldContainer else PureWhite
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (showOnlyBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                tint = if (showOnlyBookmarked) GoldDark else CharcoalMuted,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Bookmarks (محفوظ شدہ)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (showOnlyBookmarked) GoldDark else CharcoalMuted,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
        }

        // Chapters List
        items(filteredChapters, key = { it.id }) { chapter ->
            ChapterCardItem(
                chapter = chapter,
                onClick = { onChapterClick(chapter) },
                onToggleBookmark = { onToggleBookmark(chapter) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ChapterCardItem(
    chapter: BookChapter,
    onClick: () -> Unit,
    onToggleBookmark: () -> Unit
) {
    GoldBorderCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        onClick = onClick,
        testTag = "chapter_card_${chapter.chapterNumber}"
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Chapter Number Circle with Gold Accent
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(GoldContainer)
                        .border(1.5.dp, GoldPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${chapter.chapterNumber}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShimmerBadge(text = chapter.category)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${chapter.estimatedMinutes} min read",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = CharcoalMuted,
                                fontSize = 11.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = chapter.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = CharcoalText,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 20.sp
                        )
                    )

                    Text(
                        text = chapter.urduTitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = GoldDark,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (chapter.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (chapter.isBookmarked) GoldPrimary else CharcoalFaint
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = chapter.summary,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CharcoalMuted,
                    lineHeight = 19.sp
                ),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Reading Progress Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    progress = { chapter.progressPercent / 100f },
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = GoldPrimary,
                    trackColor = GoldDivider
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${chapter.progressPercent}%",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GoldDark,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
private fun ChapterDetailView(
    chapter: BookChapter,
    isTtsPlaying: Boolean,
    onBack: () -> Unit,
    onToggleBookmark: () -> Unit,
    onToggleTts: () -> Unit,
    onUpdateProgress: (Int) -> Unit
) {
    BackHandler { onBack() }

    var fontScale by remember { mutableFloatStateOf(16f) }
    var currentProgress by remember { mutableFloatStateOf(chapter.progressPercent.toFloat()) }
    var showFontControls by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PearlBackground)
            .testTag("chapter_detail_view")
    ) {
        // Regal Top Bar
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldDark
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Audio Listen Button (TTS)
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { onToggleTts() }
                            .border(1.dp, GoldPrimary, RoundedCornerShape(20.dp)),
                        color = if (isTtsPlaying) GoldContainer else PureWhite
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (isTtsPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                                contentDescription = if (isTtsPlaying) "Stop" else "Listen",
                                tint = GoldDark,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isTtsPlaying) "Stop Audio" else "Listen (سنیں)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = GoldDark,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(onClick = { showFontControls = !showFontControls }) {
                        Icon(
                            imageVector = Icons.Default.FormatSize,
                            contentDescription = "Font Size",
                            tint = if (showFontControls) GoldPrimary else CharcoalMuted
                        )
                    }

                    IconButton(onClick = onToggleBookmark) {
                        Icon(
                            imageVector = if (chapter.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (chapter.isBookmarked) GoldPrimary else CharcoalMuted
                        )
                    }
                }
            }
        }

        // Optional Font Size Adjuster Drawer
        AnimatedVisibility(visible = showFontControls) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = GoldContainer,
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Font Size: ${fontScale.toInt()}sp",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = GoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        GoldOutlinedButton(
                            text = "A-",
                            onClick = { if (fontScale > 13f) fontScale -= 2f },
                            modifier = Modifier.height(36.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        GoldOutlinedButton(
                            text = "A+",
                            onClick = { if (fontScale < 26f) fontScale += 2f },
                            modifier = Modifier.height(36.dp)
                        )
                    }
                }
            }
        }

        // Reading Content Body
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 16.dp)
        ) {
            // Chapter Header Badge
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ShimmerBadge(text = "Chapter ${chapter.chapterNumber}")
                Spacer(modifier = Modifier.width(8.dp))
                ShimmerBadge(text = chapter.category)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = chapter.title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = CharcoalText,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30.sp
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = chapter.urduTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = GoldDark,
                    fontWeight = FontWeight.Medium
                )
            )

            GoldOrnamentalDivider()

            // Key Formulas & Terms Callout Card
            if (chapter.keyFormulasAndTerms.isNotBlank()) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.2.dp, GoldBorder, RoundedCornerShape(14.dp)),
                    color = PureWhite,
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "✨ Key Syllabus Formulas & Concepts",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = GoldDark,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = chapter.keyFormulasAndTerms,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = CharcoalText,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 20.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
            }

            // Full Chapter Text
            Text(
                text = chapter.fullContent,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = fontScale.sp,
                    lineHeight = (fontScale * 1.55f).sp,
                    color = CharcoalText
                )
            )

            Spacer(modifier = Modifier.height(28.dp))
            GoldOrnamentalDivider()

            // Reading Completion Progress Slider
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mark Chapter Progress (مطالعہ کی پیشرفت)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = GoldDark,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        )
                        Text(
                            text = "${currentProgress.toInt()}%",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = CharcoalText,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Slider(
                        value = currentProgress,
                        onValueChange = { currentProgress = it },
                        onValueChangeFinished = { onUpdateProgress(currentProgress.toInt()) },
                        valueRange = 0f..100f,
                        colors = SliderDefaults.colors(
                            thumbColor = GoldPrimary,
                            activeTrackColor = GoldPrimary,
                            inactiveTrackColor = GoldDivider
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        GoldButton(
                            text = if (currentProgress >= 100f) "Completed ✓" else "Mark Complete (100%)",
                            onClick = {
                                currentProgress = 100f
                                onUpdateProgress(100)
                            },
                            modifier = Modifier.height(40.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
