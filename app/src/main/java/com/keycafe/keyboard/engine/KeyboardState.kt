package com.keycafe.keyboard.engine

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
data class KeyboardUiState(
    val currentLayout: LayoutType = LayoutType.QWERTY,
    val isShiftActive: Boolean = false,
    val isCapsLockOn: Boolean = false,
    val previewKey: Char? = null,
    val previewPosition: Pair<Float, Float> = 0f to 0f
)

enum class LayoutType { QWERTY, SYMBOLS, NUMBERS }

@Serializable
sealed interface KeyAction {
    @Serializable data object Backspace : KeyAction
    @Serializable data object Enter : KeyAction
    @Serializable data object Space : KeyAction
    @Serializable data object ShiftToggle : KeyAction
    @Serializable data object SwitchToSymbols : KeyAction
    @Serializable data object SwitchToNumbers : KeyAction
    @Serializable data object SwitchToQwerty : KeyAction
    @Serializable data object StartVoiceInput : KeyAction
    @Serializable data class CommitText(val text: String) : KeyAction
}
