package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MemoryCard
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
fun PuzzlesScreen(
    viewModel: MainStudyViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Concept Match", "Word Scramble", "Trivia Quest")
    val urduTitles = listOf("کارڈز میچنگ", "لفظی پہیلی", "علمی کوئز")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PearlBackground)
            .testTag("puzzles_screen")
    ) {
        LuxuryHeader(
            title = "SYLLABUS PUZZLES & GAMES",
            subtitle = "Sharpen your knowledge with brain games and concept challenges",
            urduTitle = "پزلز اور تعلیمی کھیل"
        )

        // Luxury Gold Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = PureWhite,
            contentColor = GoldDark,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = GoldPrimary,
                    height = 3.dp
                )
            },
            divider = { HorizontalDivider(color = GoldDivider, thickness = 1.dp) }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                    color = if (selectedTab == index) GoldDark else CharcoalMuted
                                )
                            )
                            Text(
                                text = urduTitles[index],
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (selectedTab == index) GoldPrimary else CharcoalFaint,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> MemoryGameTab(viewModel)
            1 -> WordScrambleTab(viewModel)
            2 -> TriviaQuestTab(viewModel)
        }
    }
}

// -------------------------------------------------------------
// GAME 1: CONCEPT MATCH MEMORY GAME
// -------------------------------------------------------------
@Composable
private fun MemoryGameTab(viewModel: MainStudyViewModel) {
    val cards by viewModel.memoryCards.collectAsState()
    val moves by viewModel.moveCount.collectAsState()
    val matches by viewModel.matchedCount.collectAsState()
    val isGameWon = matches >= 4

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .testTag("memory_game_tab")
    ) {
        // Score & Stats Card
        GoldBorderCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Matches: $matches / 4",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = GoldDark,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Moves: $moves",
                            style = MaterialTheme.typography.bodySmall.copy(color = CharcoalMuted)
                        )
                    }
                }

                GoldOutlinedButton(
                    text = "Reset Game",
                    onClick = { viewModel.resetMemoryGame() },
                    icon = Icons.Default.Refresh,
                    modifier = Modifier.height(36.dp)
                )
            }
        }

        if (isGameWon) {
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, GoldPrimary, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                color = GoldContainer
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Outstanding Mastery! (بہت خوب)",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = GoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "You successfully paired all syllabus concepts in $moves moves!",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = CharcoalText,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4x2 Cards Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(cards, key = { it.id }) { card ->
                MemoryCardItem(
                    card = card,
                    onClick = { viewModel.onCardClicked(card.id) }
                )
            }
        }
    }
}

@Composable
private fun MemoryCardItem(
    card: MemoryCard,
    onClick: () -> Unit
) {
    val isShown = card.isFaceUp || card.isMatched

    val backgroundColor by animateColorAsState(
        targetValue = when {
            card.isMatched -> GoldContainer
            isShown -> PureWhite
            else -> GoldContainer.copy(alpha = 0.4f)
        },
        animationSpec = tween(300),
        label = "card_bg"
    )

    val borderColor by animateColorAsState(
        targetValue = when {
            card.isMatched -> GoldAccent
            isShown -> GoldPrimary
            else -> GoldBorder
        },
        animationSpec = tween(300),
        label = "card_border"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.25f)
            .shadow(2.dp, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(backgroundColor)
            .border(1.2.dp, borderColor, RoundedCornerShape(14.dp))
            .clickable(enabled = !card.isMatched && !card.isFaceUp) { onClick() }
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isShown) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (card.isConceptName) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = card.text,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (card.isMatched) GoldDark else CharcoalText,
                        fontWeight = if (card.isConceptName) FontWeight.Bold else FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        fontSize = if (card.isConceptName) 13.sp else 11.sp,
                        lineHeight = 15.sp
                    )
                )
            }
        } else {
            // Card Back (Golden Diamond Emblem)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = GoldSecondary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "TAP TO FLIP",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GoldDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp,
                        letterSpacing = 1.sp
                    )
                )
            }
        }
    }
}

// -------------------------------------------------------------
// GAME 2: SYLLABUS WORD SCRAMBLE
// -------------------------------------------------------------
@Composable
private fun WordScrambleTab(viewModel: MainStudyViewModel) {
    val wordObj by viewModel.currentScrambleWord.collectAsState()
    val score by viewModel.scrambleScore.collectAsState()
    val feedback by viewModel.scrambleFeedback.collectAsState()
    var userGuess by remember(wordObj.originalWord) { mutableStateOf("") }
    var showHint by remember(wordObj.originalWord) { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .testTag("word_scramble_tab")
    ) {
        // Score Bar
        GoldBorderCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Extension,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Score: $score Points",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                ShimmerBadge(text = wordObj.category)
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Main Puzzle Area
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.2.dp, GoldBorder, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            color = PureWhite
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "UNSCRAMBLE THE SYLLABUS TERM",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GoldDark,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Golden Scrambled Letters Tiles Row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    wordObj.scrambledWord.forEach { char ->
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(GoldContainer)
                                .border(1.dp, GoldPrimary, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = char.toString(),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = GoldDark,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Hint Accordion
                if (showHint) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, GoldDivider, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        color = GoldContainer.copy(alpha = 0.5f)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = wordObj.hint,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = CharcoalText,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                } else {
                    GoldOutlinedButton(
                        text = "Show Syllabus Hint (ہنٹ دیکھیں)",
                        onClick = { showHint = true },
                        icon = Icons.Default.Lightbulb,
                        modifier = Modifier.height(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // User Guess Input
                OutlinedTextField(
                    value = userGuess,
                    onValueChange = { userGuess = it.uppercase() },
                    placeholder = { Text("Enter unscrambled word...", color = CharcoalMuted) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("scramble_guess_input"),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = PureWhite,
                        unfocusedContainerColor = PureWhite,
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = GoldBorder
                    )
                )

                if (feedback != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = feedback!!,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (feedback!!.contains("Correct")) GoldDark else Color(0xFFD32F2F),
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GoldOutlinedButton(
                        text = "Skip Word",
                        onClick = { viewModel.skipScrambleWord() },
                        modifier = Modifier.weight(1f)
                    )
                    GoldButton(
                        text = "Check Word ✓",
                        onClick = { viewModel.submitScrambleGuess(userGuess) },
                        modifier = Modifier.weight(1f),
                        testTag = "submit_scramble_button"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// -------------------------------------------------------------
// GAME 3: ACADEMIC TRIVIA QUEST
// -------------------------------------------------------------
@Composable
private fun TriviaQuestTab(viewModel: MainStudyViewModel) {
    val question by viewModel.currentTrivia.collectAsState()
    val selectedOption by viewModel.selectedTriviaOption.collectAsState()
    val score by viewModel.triviaScore.collectAsState()
    val streak by viewModel.triviaStreak.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .testTag("trivia_quest_tab")
    ) {
        // Stats Card
        GoldBorderCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Score: $score",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "🔥 Streak: $streak",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = GoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    ShimmerBadge(text = question.category)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Question Card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.2.dp, GoldBorder, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            color = PureWhite
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Question ${question.id}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GoldDark,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = question.question,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = CharcoalText,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 24.sp
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = question.urduQuestion,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = GoldDark,
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Options List
                question.options.forEachIndexed { index, optionText ->
                    val isSelected = selectedOption == index
                    val isCorrect = index == question.correctIndex
                    val isSubmitted = selectedOption != null

                    val optionBgColor = when {
                        !isSubmitted -> if (isSelected) GoldContainer else PureWhite
                        isCorrect -> GoldContainer
                        isSelected -> Color(0xFFFFEBEE)
                        else -> PureWhite
                    }

                    val optionBorderColor = when {
                        !isSubmitted -> if (isSelected) GoldPrimary else GoldBorder
                        isCorrect -> GoldPrimary
                        isSelected -> Color(0xFFEF5350)
                        else -> GoldDivider
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.2.dp, optionBorderColor, RoundedCornerShape(12.dp))
                            .clickable(enabled = !isSubmitted) {
                                viewModel.selectTriviaOption(index)
                            },
                        color = optionBgColor
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${('A' + index)}.  $optionText",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = CharcoalText,
                                    fontWeight = if (isSelected || (isSubmitted && isCorrect)) FontWeight.Bold else FontWeight.Normal
                                ),
                                modifier = Modifier.weight(1f)
                            )

                            if (isSubmitted) {
                                if (isCorrect) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Correct",
                                        tint = GoldDark,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Incorrect",
                                        tint = Color(0xFFD32F2F),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Explanation Box after answering
                if (selectedOption != null) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, GoldBorder, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        color = GoldContainer.copy(alpha = 0.5f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "💡 Syllabus Explanation",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = GoldDark,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = question.explanation,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = CharcoalText,
                                    lineHeight = 18.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    GoldButton(
                        text = "Next Question → (اگلا سوال)",
                        onClick = { viewModel.nextTriviaQuestion() },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
