package com.keycafe.keyboard.engine

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyboardViewModel @Inject constructor() {
    private val _uiState = MutableStateFlow(KeyboardUiState())
    val uiState = _uiState.asStateFlow()

    fun toggleShift() {
        _uiState.update {
            it.copy(
                isShiftActive = !it.isShiftActive && !it.isCapsLockOn,
                isCapsLockOn = if (it.isShiftActive) true else false
            )
        }
    }

    fun switchLayout(layout: LayoutType) {
        _uiState.update { it.copy(currentLayout = layout) }
    }

    fun showPreview(char: Char, x: Float, y: Float) {
        _uiState.update { it.copy(previewKey = char, previewPosition = x to y) }
    }

    fun hidePreview() {
        _uiState.update { it.copy(previewKey = null) }
    }
}
