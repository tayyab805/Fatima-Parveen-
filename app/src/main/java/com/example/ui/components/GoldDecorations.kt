package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.ui.theme.PureWhite

@Composable
fun GoldBorderCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    testTag: String = "gold_border_card",
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .testTag(testTag)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = GoldBorder.copy(alpha = 0.4f),
                ambientColor = GoldBorder.copy(alpha = 0.2f)
            )
            .border(1.dp, GoldBorder, RoundedCornerShape(16.dp))
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = PureWhite,
            contentColor = CharcoalText
        )
    ) {
        content()
    }
}

@Composable
fun GoldButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    testTag: String = "gold_button",
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .testTag(testTag)
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = if (enabled) GoldShimmerBrush else Brush.linearGradient(listOf(Color.LightGray, Color.Gray))
            )
            .clickable(enabled = enabled) { onClick() }
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = PureWhite,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = PureWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Composable
fun GoldOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    testTag: String = "gold_outlined_button"
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .testTag(testTag)
            .height(48.dp),
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, GoldPrimary),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = PureWhite,
            contentColor = GoldDark
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = GoldDark,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            )
        }
    }
}

@Composable
fun GoldOrnamentalDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = GoldDivider
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = GoldSecondary,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = GoldDivider
        )
    }
}

@Composable
fun ShimmerBadge(
    text: String,
    modifier: Modifier = Modifier,
    isUrdu: Boolean = false
) {
    Surface(
        modifier = modifier
            .border(0.8.dp, GoldBorder, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        color = GoldContainer
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = if (isUrdu) {
                MaterialTheme.typography.bodySmall.copy(
                    color = GoldDark,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                )
            } else {
                MaterialTheme.typography.labelSmall.copy(
                    color = GoldDark,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp
                )
            }
        )
    }
}

@Composable
fun LuxuryHeader(
    title: String,
    subtitle: String,
    urduTitle: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        if (urduTitle != null) {
            Text(
                text = urduTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = GoldDark,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge.copy(
                color = CharcoalText,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = CharcoalMuted
            )
        )
    }
}
