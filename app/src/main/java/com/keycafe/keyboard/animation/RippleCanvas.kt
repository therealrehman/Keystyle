package com.keycafe.keyboard.animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun RippleOverlay(
    engine: RippleEngine,
    modifier: Modifier = Modifier
) {
    // The infinite transition state must be READ (via `by`) so Compose
    // registers this composable as an observer and triggers recomposition
    // on every frame (16ms). Without reading it, the Canvas would never
    // invalidate and the ripple animation would be completely frozen.
    val infiniteTransition = rememberInfiniteTransition(label = "ripple_loop")
    val tick by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 16, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time_tick"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val currentTime = System.nanoTime()

        for (i in engine.activeRipples.indices.reversed()) {
            val ripple = engine.activeRipples[i]
            val elapsedMs = (currentTime - ripple.startTime) / 1_000_000f
            val progress = (elapsedMs / ripple.config.durationMs).coerceIn(0f, 1f)

            if (progress >= 1f) {
                engine.removeRipple(ripple.id)
                continue
            }

            val easedProgress = 1f - (1f - progress) * (1f - progress)
            val currentRadius = ripple.config.maxRadius * easedProgress
            val currentAlpha = ripple.config.opacity * (1f - progress)

            drawRipple(
                center = ripple.center,
                radius = currentRadius,
                alpha = currentAlpha,
                config = ripple.config
            )
        }
    }
}

private fun DrawScope.drawRipple(
    center: Offset,
    radius: Float,
    alpha: Float,
    config: RippleConfig
) {
    if (radius <= 0f || alpha <= 0f) return

    val brush = GradientUtils.create4ColorRadial(
        center = center,
        radius = radius,
        config = config.copy(opacity = alpha)
    )

    drawCircle(brush = brush, radius = radius, center = center)
}
