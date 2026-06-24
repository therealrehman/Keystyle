package com.keycafe.keyboard.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
    }

    val vibrationEnabled: Flow<Boolean> = context.keyboardDataStore.data.map { it[VIBRATION_ENABLED] ?: true }
    val soundEnabled: Flow<Boolean> = context.keyboardDataStore.data.map { it[SOUND_ENABLED] ?: true }

    suspend fun setVibration(enabled: Boolean) {
        context.keyboardDataStore.edit { it[VIBRATION_ENABLED] = enabled }
    }
}
