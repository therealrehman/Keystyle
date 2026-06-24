package com.keycafe.keyboard.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.keycafe.keyboard.theme.KeyboardTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val ACTIVE_THEME_ID = stringPreferencesKey("active_theme_id")
    }

    val activeThemeId: Flow<String> = context.dataStore.data.map { 
        it[ACTIVE_THEME_ID] ?: KeyboardTheme.DEFAULT.id 
    }

    suspend fun setActiveTheme(themeId: String) {
        context.dataStore.edit { it[ACTIVE_THEME_ID] = themeId }
    }

    fun getBuiltInThemes(): List<KeyboardTheme> = listOf(
        KeyboardTheme.DEFAULT,
        KeyboardTheme.NEON_DARK
    )
}
