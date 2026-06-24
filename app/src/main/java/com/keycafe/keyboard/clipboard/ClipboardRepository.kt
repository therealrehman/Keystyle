package com.keycafe.keyboard.clipboard

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class ClipboardItem(
    val text: String, 
    val isPinned: Boolean = false, 
    val timestamp: Long = System.currentTimeMillis()
)

@Singleton
class ClipboardRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val json = Json { ignoreUnknownKeys = true }
    companion object { val ITEMS_KEY = stringPreferencesKey("clipboard_items") }

    val items: Flow<List<ClipboardItem>> = context.dataStore.data.map { prefs ->
        val str = prefs[ITEMS_KEY] ?: "[]"
        try { json.decodeFromString(str) } catch (e: Exception) { emptyList() }
    }

    suspend fun addItem(text: String) {
        context.dataStore.edit { prefs ->
            val current = try { 
                json.decodeFromString<List<ClipboardItem>>(prefs[ITEMS_KEY] ?: "[]") 
            } catch (e: Exception) { emptyList() }
            
            val updated = (listOf(ClipboardItem(text)) + current.filter { it.text != text }).take(50)
            prefs[ITEMS_KEY] = json.encodeToString(updated)
        }
    }
}
