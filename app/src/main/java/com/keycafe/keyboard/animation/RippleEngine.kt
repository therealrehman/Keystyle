package com.keycafe.keyboard.animation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RippleEngine @Inject constructor() {
    
    val activeRipples: SnapshotStateList<ActiveRipple> = mutableStateListOf()
    private var nextId = 0L
    private val MAX_CONCURRENT_RIPPLES = 10

    fun triggerRipple(x: Float, y: Float, config: RippleConfig) {
        if (activeRipples.size >= MAX_CONCURRENT_RIPPLES) {
            activeRipples.removeFirstOrNull()
        }
        
        activeRipples.add(
            ActiveRipple(
                id = nextId++,
                center = Offset(x, y),
                startTime = System.nanoTime(),
                config = config
            )
        )
    }

    fun removeRipple(id: Long) {
        activeRipples.removeAll { it.id == id }
    }
}
