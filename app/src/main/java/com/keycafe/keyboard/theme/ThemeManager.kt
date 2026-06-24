package com.keycafe.keyboard.theme

import com.keycafe.keyboard.data.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor(
    private val repository: ThemeRepository
) {
    val currentTheme: Flow<KeyboardTheme> = repository.activeThemeId.map { id ->
        repository.getBuiltInThemes().find { it.id == id } ?: KeyboardTheme.DEFAULT
    }

    suspend fun applyTheme(themeId: String) {
        repository.setActiveTheme(themeId)
    }
}
