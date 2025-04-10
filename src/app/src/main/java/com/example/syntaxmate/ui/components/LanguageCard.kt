package com.example.syntaxmate.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.syntaxmate.R

enum class LanguageCardSize {
    COMPACT, LARGE
}

@Composable
fun LanguageCard(
    languageName: String,
    isFavorite: Boolean = false,
    isSelected: Boolean = false,
    sizeType: LanguageCardSize = LanguageCardSize.LARGE,
    onRemoveClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onSelect: () -> Unit
) {
    val cardModifier = when (sizeType) {
        LanguageCardSize.COMPACT -> Modifier.size(width=150.dp, height = 50.dp)
        LanguageCardSize.LARGE -> Modifier.width(250.dp)
    }

    val paddingValues = when (sizeType) {
        LanguageCardSize.COMPACT -> 8.dp
        LanguageCardSize.LARGE -> 16.dp
    }

    val fontSizes = when (sizeType) {
        LanguageCardSize.COMPACT -> 10.sp
        LanguageCardSize.LARGE -> 16.sp
    }

    Card(
        modifier = cardModifier
            .padding(8.dp)
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(paddingValues),
            verticalAlignment = Alignment.CenterVertically
        ) {

            val iconResId = when (languageName) {
                "Python" -> R.drawable.ic_python_light
                "JavaScript" -> R.drawable.skill_icons__js_light
                "Java" -> R.drawable.skill_icons__java_light
                "C++" -> R.drawable.skill_icons__cpp_light
                "Kotlin" -> R.drawable.skill_icons__kotlin_light
                else -> R.drawable.ic_launcher_foreground
            }
             Icon(
                painter = painterResource(id = iconResId),
                tint= Color.Unspecified,
                contentDescription = "$languageName Icon",
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = languageName,
                fontSize = fontSizes,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            IconButton(modifier = Modifier.size(24.dp), onClick = if (sizeType == LanguageCardSize.LARGE) onFavoriteClick else onRemoveClick) {
                Icon(
                    imageVector = if (sizeType == LanguageCardSize.LARGE) {
                        if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                    } else {
                        Icons.Default.Close
                    },
                    contentDescription = if (sizeType == LanguageCardSize.LARGE) "Favorite Button" else "Remove Button",
                    tint = if (sizeType == LanguageCardSize.LARGE && isFavorite) Color.DarkGray else Color.Gray
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewLanguageCardLarge() {
    LanguageCard(
        languageName = "Python",
        isFavorite = true,
        isSelected = false,
        sizeType = LanguageCardSize.LARGE,
        onFavoriteClick = {},
        onSelect = {}
    )
}

@Preview
@Composable
fun PreviewLanguageCardCompact() {
    LanguageCard(
        languageName = "Python",
        isFavorite = false,
        isSelected = true,
        sizeType = LanguageCardSize.COMPACT,
        onFavoriteClick = {},
        onSelect = {}
    )
}
