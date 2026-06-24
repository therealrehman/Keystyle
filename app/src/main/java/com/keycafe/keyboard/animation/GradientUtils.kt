package com.keycafe.keyboard.animation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode

object GradientUtils {
    fun create4ColorRadial(center: Offset, radius: Float, config: RippleConfig): Brush {
        return Brush.radialGradient(
            colors = listOf(
                config.color1.copy(alpha = config.opacity),
                config.color2.copy(alpha = config.opacity * 0.8f),
                config.color3.copy(alpha = config.opacity * 0.5f),
                config.color4.copy(alpha = 0f)
            ),
            center = center,
            radius = radius,
            tileMode = TileMode.Clamp
        )
    }
}
