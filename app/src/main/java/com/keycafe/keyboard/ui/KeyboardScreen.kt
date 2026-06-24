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
    val rippleConfig = RippleConfig()

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

        RippleOverlay(
            engine = rippleEngine,
            modifier = Modifier.matchParentSize()
        )
    }
}

private fun getQwertyRows(isUpper: Boolean): List<List<KeyDefinition>> {
    val q = "qwertyuiop".map { 
        KeyDefinition(
            if (isUpper) it.uppercase() else it.toString(), 
            KeyAction.CommitText(if (isUpper) it.uppercase() else it.toString())
        ) 
    }
    val a = "asdfghjkl".map { 
        KeyDefinition(
            if (isUpper) it.uppercase() else it.toString(), 
            KeyAction.CommitText(if (isUpper) it.uppercase() else it.toString())
        ) 
    }
    val z = "zxcvbnm".map { 
        KeyDefinition(
            if (isUpper) it.uppercase() else it.toString(), 
            KeyAction.CommitText(if (isUpper) it.uppercase() else it.toString())
        ) 
    }
    val zRow = listOf(
        KeyDefinition("⇧", KeyAction.ShiftToggle, 1.5f, true)
    ) + z + listOf(
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

private fun getSymbolRows(): List<List<KeyDefinition>> = listOf(
    listOf("1","2","3","4","5","6","7","8","9","0").map { 
        KeyDefinition(it, KeyAction.CommitText(it)) 
    },
    listOf("!","@","#","$","%","^","&","*","(",")").map { 
        KeyDefinition(it, KeyAction.CommitText(it)) 
    },
    listOf(
        KeyDefinition("ABC", KeyAction.SwitchToQwerty, 1.5f, true),
        KeyDefinition("-", KeyAction.CommitText("-")),
        KeyDefinition("+", KeyAction.CommitText("+")),
        KeyDefinition("=", KeyAction.CommitText("=")),
        KeyDefinition("/", KeyAction.CommitText("/")),
        KeyDefinition("_", KeyAction.CommitText("_")),
        KeyDefinition("⌫", KeyAction.Backspace, 1.5f, true)
    ),
    listOf(
        KeyDefinition("123", KeyAction.SwitchToNumbers, 1.5f, true),
        KeyDefinition(",", KeyAction.CommitText(",")),
        KeyDefinition(" ", KeyAction.Space, 4f),
        KeyDefinition(".", KeyAction.CommitText(".")),
        KeyDefinition("↵", KeyAction.Enter, 1.5f, true)
    )
)

private fun getNumberRows(): List<List<KeyDefinition>> = listOf(
    listOf("1","2","3","4","5","6","7","8","9","0").map { 
        KeyDefinition(it, KeyAction.CommitText(it)) 
    },
    listOf(
        KeyDefinition("+", KeyAction.CommitText("+")),
        KeyDefinition("-", KeyAction.CommitText("-")),
        KeyDefinition("×", KeyAction.CommitText("×")),
        KeyDefinition("÷", KeyAction.CommitText("÷")),
        KeyDefinition("=", KeyAction.CommitText("=")),
        KeyDefinition("%", KeyAction.CommitText("%")),
        KeyDefinition(".", KeyAction.CommitText(".")),
        KeyDefinition("(", KeyAction.CommitText("(")),
        KeyDefinition(")", KeyAction.CommitText(")")),
        KeyDefinition("^", KeyAction.CommitText("^"))
    ),
    listOf(
        KeyDefinition("ABC", KeyAction.SwitchToQwerty, 1.5f, true),
        KeyDefinition("⌫", KeyAction.Backspace, 1.5f, true)
    ),
    listOf(
        KeyDefinition("?123", KeyAction.SwitchToSymbols, 1.5f, true),
        KeyDefinition(" ", KeyAction.Space, 4f),
        KeyDefinition("↵", KeyAction.Enter, 1.5f, true)
    )
)
