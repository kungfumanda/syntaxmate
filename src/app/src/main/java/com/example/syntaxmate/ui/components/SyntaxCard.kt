package com.example.syntaxmate.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.syntaxmate.R


@Composable
fun SyntaxCard(
    languageName: String,
    syntaxTitle: String,
    codeSnippet: String,
    isFavorite: Boolean = false,
    tags: List<String>,
    onFavoriteClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // Top row with icon, title and favorite
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val iconResId = when (languageName) {
                    "Python" -> R.drawable.ic_python_light
                    else -> R.drawable.ic_launcher_foreground
                }

                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = "$languageName Icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = syntaxTitle,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }

        // expandable
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(modifier = Modifier.padding(top = 5.dp)) {

                Text(
                    text = "Language: $languageName",
                    fontSize = 12.sp
                )

                // Code box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5))
                        .padding(8.dp)
                        .horizontalScroll(rememberScrollState())
                ) {
                    Text(
                        text = codeSnippet,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Tags
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    tags.forEach { tag ->
                        AssistChip(
                            onClick = {},
                            label = { Text(tag) }
                        )
                    }

                }
            }

        }
    }

}

@Composable
fun TestSyntaxCard() {
    var isFavorite by remember { mutableStateOf(false) }

    SyntaxCard(
        languageName = "Python",
        syntaxTitle = "For Loop",
        codeSnippet = """
            for i in range(5):
                print(i)
        """.trimIndent(),
        tags = listOf("loop", "iteration", "beginner"),
        isFavorite = isFavorite,
        onFavoriteClick = { isFavorite = !isFavorite }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSyntaxCard() {
    SyntaxCard(
        languageName = "Python",
        syntaxTitle = "Loop For",
        codeSnippet = "for i in range(5):\n    print(i)",
        tags = listOf("laço", "repetição", "python"),
        isFavorite = true
    )
}
