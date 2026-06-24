package com.keycafe.keyboard.theme

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor() {
    private val _currentTheme = MutableStateFlow(KeyboardTheme.DEFAULT)
    val currentTheme = _currentTheme.asStateFlow()

    fun setTheme(theme: KeyboardTheme) {
        _currentTheme.value = theme
    }
}
