package com.keycafe.keyboard.engine

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class KeyboardViewModel : ViewModel() {
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
}
