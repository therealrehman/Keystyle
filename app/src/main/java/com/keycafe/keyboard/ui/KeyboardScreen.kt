package com.keycafe.keyboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keycafe.keyboard.animation.RippleConfig
import com.keycafe.keyboard.animation.RippleEngine
import com.keycafe.keyboard.animation.RippleOverlay
import com.keycafe.keyboard.engine.KeyAction
import com.keycafe.keyboard.engine.KeyboardUiState
import com.keycafe.keyboard.engine.LayoutType
import com.keycafe.keyboard.theme.KeyboardTheme

@Composable
fun KeyboardScreen(
    state: KeyboardUiState,
    theme: KeyboardTheme,
    rippleEngine: RippleEngine,
    onKeyAction: (KeyAction) -> Unit
) {
    val rippleConfig = RippleConfig() // Default config, later from settings

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(theme.backgroundColor)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ToolbarPlaceholder()

            val rows = when (state.currentLayout) {
                LayoutType.QWERTY -> getQwertyRows(state.isShiftActive || state.isCapsLockOn)
                LayoutType.SYMBOLS -> getSymbolRows()
                LayoutType.NUMBERS -> getNumberRows()
            }

            rows.forEach { rowKeys ->
                Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    rowKeys.forEach { key ->
                        RippleKeyboardKey(
                            key = key,
                            theme = theme,
                            rippleEngine = rippleEngine,
                            rippleConfig = rippleConfig,
                            onKeyAction = onKeyAction
                        )
                    }
                }
            }
        }

        // Animation Overlay (Top Layer)
        RippleOverlay(
            engine = rippleEngine,
            modifier = Modifier.matchParentSize()
        )
    }
}

private fun getQwertyRows(isUpper: Boolean): List<List<KeyDefinition>> {
    val q = "qwertyuiop".map { KeyDefinition(if (isUpper) it.uppercase() else it.toString(), KeyAction.CommitText(if (isUpper) it.uppercase() else it.toString())) }
    val a = "asdfghjkl".map { KeyDefinition(if (isUpper) it.uppercase() else it.toString(), KeyAction.CommitText(if (isUpper) it.uppercase() else it.toString())) }
    val zRow = listOf(
        KeyDefinition("⇧", KeyAction.ShiftToggle, 1.5f, true),
        *"zxcvbnm".map { KeyDefinition(if (isUpper) it.uppercase() else it.toString(), KeyAction.CommitText(if (isUpper) it.uppercase() else it.toString())) }.toTypedArray(),
        KeyDefinition("⌫", KeyAction.Backspace, 1.5f, true)
    )
    val bottom = listOf(
        KeyDefinition("?123", KeyAction.SwitchToSymbols, 1.5f, true),
        KeyDefinition(",", KeyAction.CommitText(",")),
        KeyDefinition(" ", KeyAction.Space, 4f),
        KeyDefinition(".", KeyAction.CommitText(".")),
        KeyDefinition("↵", KeyAction.Enter, 1.5f, true)
    )
    return listOf(q, a, zRow, bottom)
}
private fun getSymbolRows(): List<List<KeyDefinition>> = listOf(listOf(KeyDefinition("!", KeyAction.CommitText("!"))))
private fun getNumberRows(): List<List<KeyDefinition>> = listOf(listOf(KeyDefinition("1", KeyAction.CommitText("1"))))
