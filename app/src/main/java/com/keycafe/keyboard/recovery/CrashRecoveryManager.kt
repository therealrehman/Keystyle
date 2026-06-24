package com.keycafe.keyboard.recovery

import android.util.Log
import com.keycafe.keyboard.theme.KeyboardTheme
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrashRecoveryManager @Inject constructor() {
    companion object { private const val TAG = "KeyCafeRecovery" }

    fun safeGetTheme(fallback: () -> KeyboardTheme?): KeyboardTheme {
        return try { fallback() ?: KeyboardTheme.DEFAULT } 
        catch (e: Exception) { 
            Log.e(TAG, "Theme corrupted, fallback", e); KeyboardTheme.DEFAULT 
        }
    }
}
