package com.keycafe.keyboard.performance

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerformanceMonitor @Inject constructor() {
    companion object { private const val TAG = "KeyCafePerf" }

    fun recordTouchLatency(latencyMs: Long) {
        if (latencyMs > 16L) Log.w(TAG, "⚠️ Touch latency: ${latencyMs}ms")
    }

    fun recordMemoryUsage(mb: Float) {
        if (mb > 150f) Log.e(TAG, "🔴 MEMORY WARNING: ${mb}MB")
    }
}
