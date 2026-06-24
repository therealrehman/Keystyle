package com.keycafe.keyboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keycafe.keyboard.engine.KeyAction
import com.keycafe.keyboard.theme.KeyboardTheme

@Composable
fun KeyboardKey(
    key: KeyDefinition,
    theme: KeyboardTheme,
    onKeyAction: (KeyAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .weight(key.widthWeight)
            .padding(theme.keySpacing.dp)
            .clip(RoundedCornerShape(theme.cornerRadius.dp))
            .background(
                if (key.isSpecial) theme.specialKeyColor 
                else theme.keyColor
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onKeyAction(key.action) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = key.label,
            fontSize = 20.sp,
            color = if (key.isSpecial) theme.accentColor 
                    else theme.keyTextColor
        )
    }
}
