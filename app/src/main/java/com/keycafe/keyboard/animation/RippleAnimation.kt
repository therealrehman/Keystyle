package com.keycafe.keyboard.animation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Immutable
data class RippleConfig(
    val color1: Color = Color(0xFFD0BCFF),
    val color2: Color = Color(0xFFEFB8C8),
    val color3: Color = Color(0xFFA8EAD6),
    val color4: Color = Color(0xFFFFF59D),
    val maxRadius: Float = 120f,
    val durationMs: Int = 350,
    val opacity: Float = 0.8f
)

data class ActiveRipple(
    val id: Long,
    val center: Offset,
    val startTime: Long,
    val config: RippleConfig
)
