package com.keycafe.keyboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keycafe.keyboard.engine.KeyAction

@Composable
fun ToolbarPlaceholder(onKeyAction: (KeyAction) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(MaterialTheme.colorScheme.surfaceContainerHighest),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(Icons.Default.ContentPaste, contentDescription = "Clipboard", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Icon(Icons.Default.EmojiEmotions, contentDescription = "Emoji", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        // Mic button -- wired to StartVoiceInput so tapping it actually
        // triggers VoiceInputHelper.startListening() via CoreImeService,
        // instead of being a non-functional decorative icon.
        Icon(
            Icons.Default.Mic,
            contentDescription = "Voice input",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.clickable { onKeyAction(KeyAction.StartVoiceInput) }
        )
        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
