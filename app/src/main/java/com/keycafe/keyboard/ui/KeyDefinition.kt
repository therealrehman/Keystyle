package com.keycafe.keyboard.ui

import androidx.compose.runtime.Immutable
import com.keycafe.keyboard.engine.KeyAction

@Immutable
data class KeyDefinition(
    val label: String,
    val action: KeyAction,
    val widthWeight: Float = 1f,
    val isSpecial: Boolean = false
)
