package com.keycafe.keyboard.engine

import androidx.compose.runtime.Immutable

@Immutable
data class KeyboardUiState(
    val currentLayout: LayoutType = LayoutType.QWERTY,
    val isShiftActive: Boolean = false,
    val isCapsLockOn: Boolean = false,
    val previewKey: Char? = null,
    val previewPosition: Pair<Float, Float> = 0f to 0f
)

enum class LayoutType { QWERTY, SYMBOLS, NUMBERS }

sealed interface KeyAction {
    data object Backspace : KeyAction
    data object Enter : KeyAction
    data object Space : KeyAction
    data object ShiftToggle : KeyAction
    data object SwitchToSymbols : KeyAction
    data object SwitchToNumbers : KeyAction
    data object SwitchToQwerty : KeyAction
    data class CommitText(val text: String) : KeyAction
}
