package com.keycafe.keyboard.service

import android.inputmethodservice.InputMethodService
import android.view.View
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import com.keycafe.keyboard.engine.KeyAction
import com.keycafe.keyboard.engine.KeyboardViewModel
import com.keycafe.keyboard.engine.LayoutType
import com.keycafe.keyboard.theme.KeyboardTheme
import com.keycafe.keyboard.theme.ThemeManager
import com.keycafe.keyboard.animation.RippleEngine
import com.keycafe.keyboard.ui.KeyboardScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CoreImeService : InputMethodService() {

    @Inject lateinit var themeManager: ThemeManager
    @Inject lateinit var rippleEngine: RippleEngine

    private val viewModel: KeyboardViewModel by viewModels()

    override fun onCreateInputView(): View {
        return ComposeView(this).apply {
            setContent {
                val state by viewModel.uiState.collectAsState()
                val theme by themeManager.currentTheme.collectAsState(initial = KeyboardTheme.DEFAULT)

                KeyboardScreen(
                    state = state,
                    theme = theme,
                    rippleEngine = rippleEngine,
                    onKeyAction = { action -> handleKeyAction(action) }
                )
            }
        }
    }

    private fun handleKeyAction(action: KeyAction) {
        when (action) {
            is KeyAction.CommitText -> currentInputConnection.commitText(action.text, 1)
            KeyAction.Backspace -> currentInputConnection.deleteSurroundingText(1, 0)
            KeyAction.Enter -> currentInputConnection.performEditorAction(android.view.inputmethod.EditorInfo.IME_ACTION_UNSPECIFIED)
            KeyAction.Space -> currentInputConnection.commitText(" ", 1)
            KeyAction.ShiftToggle -> viewModel.toggleShift()
            KeyAction.SwitchToSymbols -> viewModel.switchLayout(LayoutType.SYMBOLS)
            KeyAction.SwitchToNumbers -> viewModel.switchLayout(LayoutType.NUMBERS)
            KeyAction.SwitchToQwerty -> viewModel.switchLayout(LayoutType.QWERTY)
        }
    }
}
