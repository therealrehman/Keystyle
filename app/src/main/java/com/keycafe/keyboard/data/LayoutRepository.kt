package com.keycafe.keyboard.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.keycafe.keyboard.builder.CustomLayout
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LayoutRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    companion object {
        private val ACTIVE_LAYOUT_JSON = stringPreferencesKey("active_layout_json")
    }

    private val json = Json { ignoreUnknownKeys = true; prettyPrint = true }

    val activeLayout: Flow<CustomLayout> = context.dataStore.data.map { prefs ->
        val jsonStr = prefs[ACTIVE_LAYOUT_JSON]
        if (!jsonStr.isNullOrEmpty()) {
            try { json.decodeFromString(jsonStr) } 
            catch (_: Exception) { CustomLayout() }
        } else CustomLayout()
    }

    suspend fun saveActiveLayout(layout: CustomLayout) {
        context.dataStore.edit { it[ACTIVE_LAYOUT_JSON] = json.encodeToString(layout) }
    }
}
