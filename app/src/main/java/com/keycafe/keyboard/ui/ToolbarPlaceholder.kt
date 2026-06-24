package com.keycafe.keyboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ToolbarPlaceholder() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(MaterialTheme.colorScheme.surfaceContainerHighest),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(Icons.Default.ContentPaste, contentDescription = "Clipboard", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Icon(Icons.Default.EmojiEmotions, contentDescription = "Emoji", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
