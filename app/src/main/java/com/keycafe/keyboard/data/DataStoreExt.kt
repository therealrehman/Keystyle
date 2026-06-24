package com.keycafe.keyboard.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.keyboardDataStore: DataStore<Preferences> by preferencesDataStore(name = "keyboard_settings")
